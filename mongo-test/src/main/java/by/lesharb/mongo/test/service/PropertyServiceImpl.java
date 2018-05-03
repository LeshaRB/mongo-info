package by.lesharb.mongo.test.service;

import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * PropertyServiceImpl.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@Service
@Getter
public class PropertyServiceImpl implements PropertyService {
  /**
   * Mongo.
   */
  @Value("${config.database.name:admin}")
  private String mongoDatabaseName;

  @Value("#{'${mongo.database.urls:127.0.0.1:27017}'.split(',')}")
  private List<String> mongoUrls;

  @Value("${config.database.user:admin}")
  private String mongoUsername;

  @Value("${config.database.password:admin}")
  private String mongoPassword;
}
