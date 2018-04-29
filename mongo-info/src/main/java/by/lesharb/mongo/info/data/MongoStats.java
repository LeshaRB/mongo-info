package by.lesharb.mongo.info.data;

import com.google.auto.value.AutoValue;
import com.google.common.net.HostAndPort;
import java.util.List;

/**
 * MongoStats.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@AutoValue
public abstract class MongoStats {

  public abstract List<HostAndPort> servers();

  public abstract BuildInfo buildInfo();

  public abstract HostInfo hostInfo();

  public abstract ServerStatus serverStatus();

  public abstract DatabaseStats databaseStats();

  public static MongoStats create(List<HostAndPort> servers,
      BuildInfo buildInfo,
      HostInfo hostInfo,
      ServerStatus serverStatus,
      DatabaseStats databaseStats) {
    return new AutoValue_MongoStats(servers, buildInfo, hostInfo, serverStatus, databaseStats);
  }
}