package by.lesharb.mongo.info.data;

import com.google.auto.value.AutoValue;
import java.util.List;
import javax.annotation.Nullable;

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

  @Nullable
  public abstract String loaderFlags();

  @Nullable
  public abstract String compilerFlags();

  @Nullable
  public abstract String allocator();

  public abstract List<Integer> versionArray();

  @Nullable
  public abstract String javascriptEngine();

  public abstract int bits();

  public abstract boolean debug();

  public abstract int maxBsonObjectSize();

  public static BuildInfo create(String version,
      String gitVersion,
      String sysInfo,
      @Nullable String loaderFlags,
      @Nullable String compilerFlags,
      @Nullable String allocator,
      List<Integer> versionArray,
      @Nullable String javascriptEngine,
      int bits,
      boolean debug,
      int maxBsonObjectSize) {
    return new AutoValue_BuildInfo(version, gitVersion, sysInfo, loaderFlags, compilerFlags, allocator,
        versionArray, javascriptEngine, bits, debug, maxBsonObjectSize);
  }
}
