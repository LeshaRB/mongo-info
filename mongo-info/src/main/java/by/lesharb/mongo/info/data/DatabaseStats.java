package by.lesharb.mongo.info.data;

import com.google.auto.value.AutoValue;

/**
 * DatabaseStats.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@AutoValue
public abstract class DatabaseStats {

  public abstract String db();

  public abstract int collections();

  public abstract int objects();

  public abstract double avgObjSize();

  public abstract double dataSize();

  public abstract double storageSize();

  public abstract int numExtents();

  public abstract int indexes();

  public abstract double indexSize();

  public abstract Long fileSize();

  public abstract Long nsSizeMB();

  public abstract ExtentFreeList extentFreeList();

  public abstract DataFileVersion dataFileVersion();

  public static DatabaseStats create(String db,
      int collections,
      int objects,
      double avgObjSize,
      double dataSize,
      double storageSize,
      int numExtents,
      int indexes,
      double indexSize,
      Long fileSize,
      Long nsSizeMB,
      ExtentFreeList extentFreeList,
      DataFileVersion dataFileVersion) {
    return new AutoValue_DatabaseStats(db,
        collections,
        objects,
        avgObjSize,
        dataSize,
        storageSize,
        numExtents,
        indexes,
        indexSize,
        fileSize,
        nsSizeMB,
        extentFreeList,
        dataFileVersion);
  }

  @AutoValue
  public abstract static class ExtentFreeList {

    public abstract int num();

    public abstract int totalSize();

    public static ExtentFreeList create(int num, int totalSize) {
      return new AutoValue_DatabaseStats_ExtentFreeList(num, totalSize);
    }
  }

  @AutoValue
  public abstract static class DataFileVersion {

    public abstract int major();

    public abstract int minor();

    public static DataFileVersion create(int major, int minor) {
      return new AutoValue_DatabaseStats_DataFileVersion(major, minor);
    }
  }
}