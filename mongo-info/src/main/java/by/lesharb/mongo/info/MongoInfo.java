package by.lesharb.mongo.info;

import by.lesharb.mongo.info.data.BuildInfo;
import by.lesharb.mongo.info.data.DatabaseStats;
import by.lesharb.mongo.info.data.HostInfo;
import by.lesharb.mongo.info.data.MongoStats;
import by.lesharb.mongo.info.data.ServerStatus;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import com.mongodb.BasicDBObject;
import com.mongodb.ServerAddress;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * MongoInfo.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@Slf4j
public class MongoInfo {

  public MongoStats mongoStats(MongoTemplate mongoTemplate) {
    final List<ServerAddress> serverAddresses = new ArrayList<>();
    //            mongoClient.getServerAddressList();
    final List<HostAndPort> servers = Lists.newArrayListWithCapacity(serverAddresses.size());
    //    for (ServerAddress serverAddress : serverAddresses) {
    //      servers.add(HostAndPort.fromParts(serverAddress.getHost(), serverAddress.getPort()));
    //    }
    final DatabaseStats dbStats;
    final Document dbStatsResult = mongoTemplate.executeCommand(new Document("dbStats", 1));
    if (checkOk(dbStatsResult)) {
      final BasicDBObject extentFreeListMap = (BasicDBObject) dbStatsResult.get("extentFreeList");
      final DatabaseStats.ExtentFreeList extentFreeList;
      if (extentFreeListMap == null) {
        extentFreeList = null;
      } else {
        extentFreeList = DatabaseStats.ExtentFreeList.create(
            extentFreeListMap.getInt("num"),
            extentFreeListMap.getInt("totalSize")
        );
      }
      final BasicDBObject dataFileVersionMap = (BasicDBObject) dbStatsResult.get("dataFileVersion");
      final DatabaseStats.DataFileVersion dataFileVersion;
      if (dataFileVersionMap == null) {
        dataFileVersion = null;
      } else {
        dataFileVersion = DatabaseStats.DataFileVersion.create(
            dataFileVersionMap.getInt("major"),
            dataFileVersionMap.getInt("minor")
        );
      }
      dbStats = DatabaseStats.create(
          dbStatsResult.getString("db"),
          dbStatsResult.getInteger("collections"),
          dbStatsResult.getInteger("objects"),
          dbStatsResult.getDouble("avgObjSize"),
          dbStatsResult.getDouble("dataSize"),
          dbStatsResult.getDouble("storageSize"),
          dbStatsResult.getInteger("numExtents"),
          dbStatsResult.getInteger("indexes"),
          dbStatsResult.getDouble("indexSize"),
          dbStatsResult.containsKey("fileSize") ? dbStatsResult.getLong("fileSize") : -1L,
          dbStatsResult.containsKey("nsSizeMB") ? dbStatsResult.getLong("nsSizeMB") : -1L,
          extentFreeList,
          dataFileVersion
      );
    } else {
      log.debug("Couldn't retrieve MongoDB dbStats: {}", getErrorMessage(dbStatsResult));
      dbStats = null;
    }

    final ServerStatus serverStatus;
    final Document serverStatusResult = mongoTemplate.executeCommand(new Document("serverStatus", 1));
    if (checkOk(serverStatusResult)) {
      final Document connectionsMap = (Document) serverStatusResult.get("connections");
      final ServerStatus.Connections connections = ServerStatus.Connections.create(
          connectionsMap.getInteger("current"),
          connectionsMap.getInteger("available"),
          connectionsMap.containsKey("totalCreated") ? connectionsMap.getInteger("totalCreated") : -1
      );
      final Document networkMap = (Document) serverStatusResult.get("network");
      final ServerStatus.Network network = ServerStatus.Network.create(
          networkMap.getLong("bytesIn"),
          networkMap.getLong("bytesOut"),
          networkMap.getLong("numRequests")
      );
      final Document memoryMap = (Document) serverStatusResult.get("mem");
      final ServerStatus.Memory memory = ServerStatus.Memory.create(
          memoryMap.getInteger("bits"),
          memoryMap.getInteger("resident"),
          memoryMap.getInteger("virtual"),
          memoryMap.getBoolean("supported"),
          memoryMap.getInteger("mapped"),
          memoryMap.getInteger("mappedWithJournal")
      );
      final Document storageEngineMap = (Document) serverStatusResult.get("storageEngine");
      final ServerStatus.StorageEngine storageEngine;
      if (storageEngineMap == null) {
        storageEngine = ServerStatus.StorageEngine.DEFAULT;
      } else {
        storageEngine = ServerStatus.StorageEngine.create(storageEngineMap.getString("name"));
      }
      final double uptime = serverStatusResult.containsKey("uptime") ? serverStatusResult.getDouble("uptime") : 0;
      serverStatus = ServerStatus.create(
          serverStatusResult.getString("host"),
          serverStatusResult.getString("version"),
          serverStatusResult.getString("process"),
          serverStatusResult.containsKey("pid") ? serverStatusResult.getLong("pid") : 0,
          uptime,
          serverStatusResult.containsKey("uptimeMillis") ? serverStatusResult.getLong("uptimeMillis") : uptime * 1000L,
          serverStatusResult.getLong("uptimeEstimate"),
          serverStatusResult.getDate("localTime")
              .toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDateTime(),
          connections,
          network,
          memory,
          storageEngine);
    } else {
      log.debug("Couldn't retrieve MongoDB serverStatus: {}", getErrorMessage(serverStatusResult));
      serverStatus = null;
    }
    // TODO Collection stats? http://docs.mongodb.org/manual/reference/command/collStats/
    return MongoStats.create(servers, createBuildInfo(mongoTemplate), createHostInfo(mongoTemplate), serverStatus,
        dbStats);
  }

  private static HostInfo createHostInfo(MongoTemplate mongoTemplate) {
    final HostInfo hostInfo;
    final Document hostInfoResult = mongoTemplate.executeCommand(new Document("hostInfo", 1));
    if (checkOk(hostInfoResult)) {
      final Document systemMap = (Document) hostInfoResult.get("system");
      final HostInfo.System system = HostInfo.System.create(
          systemMap.getDate("currentTime")
              .toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDateTime(),
          systemMap.getString("hostname"),
          systemMap.getInteger("cpuAddrSize"),
          systemMap.getInteger("memSizeMB"),
          systemMap.getInteger("numCores"),
          systemMap.getString("cpuArch"),
          systemMap.getBoolean("numaEnabled")
      );
      final Document osMap = (Document) hostInfoResult.get("os");
      final HostInfo.Os os = HostInfo.Os.create(
          osMap.getString("type"),
          osMap.getString("name"),
          osMap.getString("version")
      );
      final Document extraMap = (Document) hostInfoResult.get("extra");
      final HostInfo.Extra extra = HostInfo.Extra.create(
          extraMap.getString("versionString"),
          extraMap.getString("libcVersion"),
          extraMap.getString("kernelVersion"),
          extraMap.getString("cpuFrequencyMHz"),
          extraMap.getString("cpuFeatures"),
          extraMap.getString("scheduler"),
          extraMap.containsKey("pageSize") ? extraMap.getLong("pageSize") : -1L,
          extraMap.containsKey("numPages") ? extraMap.getInteger("numPages") : -1,
          extraMap.containsKey("maxOpenFiles") ? extraMap.getInteger("maxOpenFiles") : -1
      );
      hostInfo = HostInfo.create(system, os, extra);
    } else {
      log.debug("Couldn't retrieve MongoDB hostInfo: {}", getErrorMessage(hostInfoResult));
      hostInfo = null;
    }
    return hostInfo;
  }

  private static BuildInfo createBuildInfo(MongoTemplate mongoTemplate) {
    final BuildInfo buildInfo;
    final Document buildInfoResult = mongoTemplate.executeCommand(new Document("buildInfo", 1));
    if (checkOk(buildInfoResult)) {
      buildInfo = BuildInfo.create(
          buildInfoResult.getString("version"),
          buildInfoResult.getString("gitVersion"),
          buildInfoResult.getString("sysInfo"),
          buildInfoResult.getString("loaderFlags"),
          buildInfoResult.getString("compilerFlags"),
          buildInfoResult.getString("allocator"),
          buildInfoResult.get("versionArray", ArrayList.class),
          buildInfoResult.getString("javascriptEngine"),
          buildInfoResult.getInteger("bits"),
          buildInfoResult.getBoolean("debug"),
          buildInfoResult.getInteger("maxBsonObjectSize")
      );
    } else {
      log.debug("Couldn't retrieve MongoDB buildInfo: {}", getErrorMessage(buildInfoResult));
      buildInfo = null;
    }
    return buildInfo;
  }

  private static boolean checkOk(Document document) {
    Object okValue = document.get("ok");
    if (okValue instanceof Boolean) {
      return (Boolean) okValue;
    } else if (okValue instanceof Number) {
      return ((Number) okValue).intValue() == 1;
    } else {
      return false;
    }
  }

  private static String getErrorMessage(Document document) {
    Object foo = document.get("errmsg");
    if (foo == null) {
      return null;
    }
    return foo.toString();
  }
}
