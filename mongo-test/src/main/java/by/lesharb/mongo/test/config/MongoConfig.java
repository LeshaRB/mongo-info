package by.lesharb.mongo.test.config;

import by.lesharb.mongo.test.service.PropertyService;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * MongoConfig.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@Slf4j
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

  @Autowired
  private PropertyService propertyService;

  private MongoClient mongoClient;

  @PostConstruct
  private void init() {
    log.debug("Using {} database and URL {}", propertyService.getMongoDatabaseName(), propertyService.getMongoUrls());
  }

  @Override
  protected String getDatabaseName() {
    return propertyService.getMongoDatabaseName();
  }

  @Bean
  public MongoClient mongoClient() {
    if (Objects.isNull(mongoClient)) {
      MongoCredential credential = MongoCredential.createCredential(propertyService.getMongoUsername(),
          propertyService.getMongoDatabaseName(), propertyService.getMongoPassword()
              .toCharArray());

      List<ServerAddress> serverAddresses = propertyService.getMongoUrls()
          .stream()
          .map(url -> url.split(":"))
          .map(urlObj -> {
            String host = urlObj[0];
            // if port is not present - set default mongod port 27017
            int port = urlObj.length > 1 ? Integer.parseInt(urlObj[1]) : 27107;
            return new ServerAddress(host, port);
          })
          .collect(Collectors.toList());

      if (serverAddresses.size() > 1) {
        mongoClient = new MongoClient(serverAddresses, credential, createMongoClientOptions());
      } else {
        mongoClient = new MongoClient(serverAddresses.get(0), credential, createMongoClientOptions());
      }
    }
    return mongoClient;
  }

  private MongoClientOptions createMongoClientOptions() {
    MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();
    /*
    if (configuration.getConnectionsPerHost() != null) {
      optionsBuilder.connectionsPerHost(configuration.getConnectionsPerHost());
    }
    if (configuration.getMaxWaitTime() != null) {
      optionsBuilder.maxWaitTime(configuration.getMaxWaitTime());
    }
    if (configuration.getConnectionTimeout() != null) {
      optionsBuilder.connectTimeout(configuration.getConnectionTimeout());
    }
    if (configuration.getSocketTimeout() != null) {
      optionsBuilder.socketTimeout(configuration.getSocketTimeout());
    }
    if (configuration.getSocketKeepalive() != null) {
      optionsBuilder.socketKeepAlive(configuration.getSocketKeepalive());
    }
    */
    return optionsBuilder.build();
  }

  @Override
  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongoDbFactory(), mongoConverter());
  }

  @Bean
  public GridFsTemplate gridFsTemplate() {
    GridFsTemplate gridFsTemplate = null;
    try {
      gridFsTemplate = new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    }
    return gridFsTemplate;
  }

  @Override
  public MongoDbFactory mongoDbFactory() {
    return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
  }

  @Bean(name = "mappingMongoConverter")
  public MappingMongoConverter mongoConverter() {
    MappingMongoConverter converter = null;
    try {
      DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
      MongoMappingContext mappingContext = new MongoMappingContext();
      mappingContext.afterPropertiesSet();
      converter = new MappingMongoConverter(dbRefResolver, mappingContext);
      converter.setMapKeyDotReplacement("~");
      converter.afterPropertiesSet();
    } catch (Exception e) {
      log.error("MappingMongoConverter create error", e);
    }
    return converter;
  }

}


