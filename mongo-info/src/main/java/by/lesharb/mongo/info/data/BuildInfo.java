package by.lesharb.mongo.info.data;

import com.google.auto.value.AutoValue;
import java.util.List;

/**
 * BuildInfo.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@AutoValue
public abstract class BuildInfo {

  public abstract String version();

  public abstract String gitVersion();

  public abstract String sysInfo();

  public abstract String loaderFlags();

  public abstract String compilerFlags();

  public abstract String allocator();

  public abstract List<Integer> versionArray();

  public abstract String javascriptEngine();

  public abstract int bits();

  public abstract boolean debug();

  public abstract long maxBsonObjectSize();

  public static BuildInfo create(String version,
      String gitVersion,
      String sysInfo,
      String loaderFlags,
      String compilerFlags,
      String allocator,
      List<Integer> versionArray,
      String javascriptEngine,
      int bits,
      boolean debug,
      long maxBsonObjectSize) {
    return new AutoValue_BuildInfo(version,
        gitVersion,
        sysInfo,
        loaderFlags,
        compilerFlags,
        allocator,
        versionArray,
        javascriptEngine,
        bits,
        debug,
        maxBsonObjectSize);
  }
}
