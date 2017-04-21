package ${package};

import static com.jayway.restassured.RestAssured.when;

import com.jayway.restassured.RestAssured;
import ${package}.entity.Greeting;
import ${package}.repository.GreetingRepository;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Integration test using RestAssured
 */
@ActiveProfiles("it.env")
@SpringBootTest(classes = {ResourcesService.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class ResourcesServiceRestAssuredIT {

  @Value("${local.server.port}")
  private Integer port;

  @Autowired
  GreetingRepository repository;

  Greeting mickey;
  Greeting minnie;
  Greeting pluto;

  @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
  @Before
  public void setUp() {
    RestAssured.port = this.port;

    this.repository.deleteAll();
    this.mickey = Greeting.greetingBuilder().toWhom("mickey").build();
    this.minnie = Greeting.greetingBuilder().toWhom("minnie").build();
    this.pluto = Greeting.greetingBuilder().toWhom("pluto").build();
    this.repository.save(Arrays.asList(this.mickey, this.minnie, this.pluto));
  }

  @Test
  public void canFetchMickey() {
    final Long mickeyId = this.mickey.getId();

    when() //
      .get("/api/greetings/{id}", mickeyId).
      then().
      statusCode(HttpStatus.SC_OK).
      body("id", Matchers.is(mickeyId.intValue())).
      body("toWhom", Matchers.is("mickey")).
      body("version", Matchers.is(0));
  }

  @Test
  public void canFetchAll() {
    when() //
      .get("/api/greetings").
      then().
      statusCode(HttpStatus.SC_OK).
      body("content.toWhom", Matchers.hasItems("mickey", "minnie", "pluto"));
  }

  @Test
  public void canDeletePluto() {
    final Long plutoId = this.pluto.getId();

    when() //
      .delete("/api/greetings/{id}", plutoId).
      then().
      statusCode(HttpStatus.SC_NO_CONTENT);
  }
}
