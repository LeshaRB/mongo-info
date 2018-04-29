package by.lesharb.mongo.info.data;

import com.google.auto.value.AutoValue;
import java.time.LocalDateTime;

/**
 * ServerStatus.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@AutoValue
public abstract class ServerStatus {

  public abstract String host();

  public abstract String version();

  public abstract String process();

  public abstract long pid();

  public abstract int uptime();

  public abstract long uptimeMillis();

  public abstract int uptimeEstimate();

  public abstract LocalDateTime localTime();

  public abstract Connections connections();

  public abstract Network network();

  public abstract Memory memory();

  public abstract StorageEngine storageEngine();

  public static ServerStatus create(String host,
      String version,
      String process,
      long pid,
      int uptime,
      long uptimeMillis,
      int uptimeEstimate,
      LocalDateTime localTime,
      Connections connections,
      Network network,
      Memory memory,
      StorageEngine storageEngine) {
    return new AutoValue_ServerStatus(host,
        version,
        process,
        pid,
        uptime,
        uptimeMillis,
        uptimeEstimate,
        localTime,
        connections,
        network,
        memory,
        storageEngine);
  }

  @AutoValue
  public abstract static class Connections {

    public abstract int current();

    public abstract int available();

    public abstract Long totalCreated();

    public static Connections create(int current, int available, Long totalCreated) {
      return new AutoValue_ServerStatus_Connections(current, available, totalCreated);
    }
  }

  @AutoValue
  public abstract static class Network {

    public abstract int bytesIn();

    public abstract int bytesOut();

    public abstract int numRequests();

    public static Network create(int bytesIn, int bytesOut, int numRequests) {
      return new AutoValue_ServerStatus_Network(bytesIn, bytesOut, numRequests);
    }
  }

  @AutoValue
  public abstract static class Memory {

    public abstract int bits();

    public abstract int resident();

    public abstract int virtual();

    public abstract boolean supported();

    public abstract int mapped();

    public abstract int mappedWithJournal();

    public static Memory create(int bits,
        int resident,
        int virtual,
        boolean supported,
        int mapped,
        int mappedWithJournal) {
      return new AutoValue_ServerStatus_Memory(bits, resident, virtual, supported, mapped, mappedWithJournal);
    }
  }

  @AutoValue
  public abstract static class StorageEngine {

    public static final StorageEngine DEFAULT = create("mmapv1");

    public abstract String name();

    public static StorageEngine create(String name) {
      return new AutoValue_ServerStatus_StorageEngine(name);
    }
  }

  // TODO Implement the remaining information from serverStatus?
}
