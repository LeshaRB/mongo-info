package by.lesharb.mongo.info.data;

import com.google.auto.value.AutoValue;
import java.time.LocalDateTime;
import javax.annotation.Nullable;

/**
 * HostInfo.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@AutoValue
public abstract class HostInfo {

  public abstract System system();

  public abstract Os os();

  public abstract Extra extra();

  public static HostInfo create(System system,
      Os os,
      Extra extra) {
    return new AutoValue_HostInfo(system, os, extra);
  }

  @AutoValue
  public abstract static class System {

    public abstract LocalDateTime currentTime();

    public abstract String hostname();

    public abstract int cpuAddrSize();

    public abstract int memSizeMB();

    public abstract int numCores();

    public abstract String cpuArch();

    public abstract boolean numaEnabled();

    public static System create(LocalDateTime currentTime,
        String hostname,
        int cpuAddrSize,
        int memSizeMB,
        int numCores,
        String cpuArch,
        boolean numaEnabled) {
      return new AutoValue_HostInfo_System(currentTime, hostname, cpuAddrSize, memSizeMB, numCores, cpuArch,
          numaEnabled);
    }
  }

  @AutoValue
  public abstract static class Os {

    public abstract String type();

    public abstract String name();

    public abstract String version();

    public static Os create(String type,
        String name,
        String version) {
      return new AutoValue_HostInfo_Os(type, name, version);
    }
  }

  @AutoValue
  public abstract static class Extra {

    @Nullable
    public abstract String versionString();

    @Nullable
    public abstract String libcVersion();

    @Nullable
    public abstract String kernelVersion();

    @Nullable
    public abstract String cpuFrequencyMHz();

    @Nullable
    public abstract String cpuFeatures();

    @Nullable
    public abstract String scheduler();

    public abstract long pageSize();

    public abstract int numPages();

    public abstract int maxOpenFiles();

    public static Extra create(@Nullable String versionString,
        @Nullable String libcVersion,
        @Nullable String kernelVersion,
        @Nullable String cpuFrequencyMHz,
        @Nullable String cpuFeatures,
        @Nullable String scheduler,
        long pageSize,
        int numPages,
        int maxOpenFiles) {
      return new AutoValue_HostInfo_Extra(versionString, libcVersion, kernelVersion, cpuFrequencyMHz, cpuFeatures,
          scheduler, pageSize, numPages, maxOpenFiles);
    }
  }
}