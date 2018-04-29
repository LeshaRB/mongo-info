package by.lesharb.mongo.info.data;

import com.google.auto.value.AutoValue;
import java.time.LocalDateTime;

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

  public static HostInfo create(System system, Os os, Extra extra) {
    return new AutoValue_HostInfo(system, os, extra);
  }

  @AutoValue
  public abstract static class System {

    public abstract LocalDateTime currentTime();

    public abstract String hostname();

    public abstract int cpuAddrSize();

    public abstract long memSizeMB();

    public abstract int numCores();

    public abstract String cpuArch();

    public abstract boolean numaEnabled();

    public static System create(LocalDateTime currentTime,
        String hostname,
        int cpuAddrSize,
        long memSizeMB,
        int numCores,
        String cpuArch,
        boolean numaEnabled) {
      return new AutoValue_HostInfo_System(currentTime,
          hostname,
          cpuAddrSize,
          memSizeMB,
          numCores,
          cpuArch,
          numaEnabled);
    }
  }

  @AutoValue
  public abstract static class Os {

    public abstract String type();

    public abstract String name();

    public abstract String version();

    public static Os create(String type, String name, String version) {
      return new AutoValue_HostInfo_Os(type, name, version);
    }
  }

  @AutoValue
  public abstract static class Extra {

    public abstract String versionString();

    public abstract String libcVersion();

    public abstract String kernelVersion();

    public abstract String cpuFrequencyMHz();

    public abstract String cpuFeatures();

    public abstract String scheduler();

    public abstract Long pageSize();

    public abstract Long numPages();

    public abstract Long maxOpenFiles();

    public static Extra create(String versionString,
        String libcVersion,
        String kernelVersion,
        String cpuFrequencyMHz,
        String cpuFeatures,
        String scheduler,
        long pageSize,
        Long numPages,
        Long maxOpenFiles) {
      return new AutoValue_HostInfo_Extra(versionString,
          libcVersion,
          kernelVersion,
          cpuFrequencyMHz,
          cpuFeatures,
          scheduler,
          pageSize,
          numPages,
          maxOpenFiles);
    }
  }
}