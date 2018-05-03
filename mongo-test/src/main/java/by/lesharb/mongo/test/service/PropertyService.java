package by.lesharb.mongo.test.service;

import java.util.List;

/**
 * PropertyService.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
public interface PropertyService {

  String getMongoDatabaseName();

  List<String> getMongoUrls();

  String getMongoUsername();

  String getMongoPassword();
}
