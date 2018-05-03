package by.lesharb.mongo.info;

import static org.junit.Assert.assertEquals;

import by.lesharb.mongo.info.data.MongoStats;
import by.lesharb.mongo.test.config.MongoConfig;
import by.lesharb.mongo.test.service.PropertyServiceImpl;
import com.google.common.net.HostAndPort;
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * MongoInfoTest.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfig.class, PropertyServiceImpl.class})
@Slf4j
public class MongoInfoTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private MongoClient mongoClient;


  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void mongoStats() {
    MongoInfo mongoInfo = new MongoInfo();
    MongoStats mongoStats = mongoInfo.mongoStats(mongoTemplate, mongoClient);
    assertEquals(mongoStats.servers()
        .size(), 1);

    HostAndPort hostAndPort = mongoStats.servers()
        .get(0);
    assertEquals(hostAndPort.getHost(), "127.0.0.1");
    assertEquals(hostAndPort.getPort(), 27017);
  }
}