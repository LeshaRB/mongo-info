package by.lesharb.mongo.info.data;

import com.google.auto.value.AutoValue;
import com.google.common.net.HostAndPort;
import java.util.List;
import javax.annotation.Nullable;

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

  @Nullable
  public abstract HostInfo hostInfo();

  @Nullable
  public abstract ServerStatus serverStatus();

  @Nullable
  public abstract DatabaseStats databaseStats();

  public static MongoStats create(List<HostAndPort> servers,
      BuildInfo buildInfo,
      @Nullable HostInfo hostInfo,
      @Nullable ServerStatus serverStatus,
      @Nullable DatabaseStats databaseStats) {
    return new AutoValue_MongoStats(servers, buildInfo, hostInfo, serverStatus, databaseStats);
  }
}