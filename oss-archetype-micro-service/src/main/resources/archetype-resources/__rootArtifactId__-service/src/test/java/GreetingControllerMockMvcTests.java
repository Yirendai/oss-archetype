package ${package};

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import ${package}.controller.GreetingController;
import ${package}.entity.Greeting;
import ${package}.service.GreetingService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@EnableSpringDataWebSupport
@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
public class GreetingControllerMockMvcTests {

  //@Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext wac;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private GreetingService greetingService;

  Greeting mickey;

  @Before
  public void setUp() {
    this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    this.mickey = new Greeting(1L, "mickey", 0L);
  }

  @Test
  public void testFindOne() throws Exception {
    given( //
      this.greetingService.findOne(this.mickey.getId()) //
    ).willReturn( //
      this.mickey //
    );

    this.mvc.perform( //
      get("/api/greetings/{id}", this.mickey.getId())
        .accept(MediaType.APPLICATION_JSON)
    ) //
      .andExpect(status().isOk()) //
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("@.toWhom").value(this.mickey.getToWhom()));
  }

  @Test
  public void testUpdate() throws Exception {
    final Greeting newMickey = Greeting.greetingBuilder().id(this.mickey.getId()).toWhom("Mickey").build();
    given( //
      this.greetingService.update(newMickey)
    ).willReturn( //
      Greeting.greetingBuilder() //
        .id(this.mickey.getId()) //
        .toWhom(newMickey.getToWhom()) //
        .version(this.mickey.getVersion() + 1L) //
        .build()
    );

    this.mvc.perform(
      put("/api/greetings/{id}", this.mickey.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsBytes(newMickey))
    ) //
      .andExpect(status().isAccepted())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("@.toWhom").value(newMickey.getToWhom()));
  }
}
