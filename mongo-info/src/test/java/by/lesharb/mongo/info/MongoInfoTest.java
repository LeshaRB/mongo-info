package by.lesharb.mongo.info;

import by.lesharb.mongo.info.data.MongoStats;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * MongoInfoTest.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MongoInfoTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void mongoStats() {
    MongoInfo mongoInfo = new MongoInfo();
    MongoStats mongoStats = mongoInfo.mongoStats(mongoTemplate);
    log.info("Test {}", mongoTemplate.getCollectionNames()
        .toString());
  }
}