package by.lesharb.mongo.info;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MongoInfoApplication.
 *
 * @author Aliaksei Labotski.
 * @since 4/29/18.
 */
@SpringBootApplication(scanBasePackages = {"by.lesharb.mongo.info"})
@Slf4j
@SuppressWarnings("PMD.UseUtilityClass")
public class MongoInfoApplication {

  public static void main(String[] args) {
    SpringApplication.run(MongoInfoApplication.class, args);
  }
}
