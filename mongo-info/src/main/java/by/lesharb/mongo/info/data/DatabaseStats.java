package by.lesharb.mongo.info.data;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * DatabaseStats.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@AutoValue
public abstract class DatabaseStats {

  public abstract String db();

  public abstract long collections();

  public abstract long objects();

  public abstract double avgObjSize();

  public abstract double dataSize();

  public abstract double storageSize();

  public abstract long numExtents();

  public abstract long indexes();

  public abstract double indexSize();

  public abstract long fileSize();

  public abstract long nsSizeMB();

  @Nullable
  public abstract ExtentFreeList extentFreeList();

  @Nullable
  public abstract DataFileVersion dataFileVersion();

  public static DatabaseStats create(String db,
      long collections,
      long objects,
      double avgObjSize,
      double dataSize,
      double storageSize,
      long numExtents,
      long indexes,
      double indexSize,
      long fileSize,
      long nsSizeMB,
      @Nullable ExtentFreeList extentFreeList,
      @Nullable DataFileVersion dataFileVersion) {
    return new AutoValue_DatabaseStats(db, collections, objects, avgObjSize, dataSize, storageSize, numExtents,
        indexes, indexSize, fileSize, nsSizeMB, extentFreeList, dataFileVersion);
  }

  @AutoValue
  public abstract static class ExtentFreeList {

    public abstract int num();

    public abstract int totalSize();

    public static ExtentFreeList create(int num,
        int totalSize) {
      return new AutoValue_DatabaseStats_ExtentFreeList(num, totalSize);
    }
  }

  @AutoValue
  public abstract static class DataFileVersion {

    public abstract int major();

    public abstract int minor();

    public static DataFileVersion create(int major,
        int minor) {
      return new AutoValue_DatabaseStats_DataFileVersion(major, minor);
    }
  }
}