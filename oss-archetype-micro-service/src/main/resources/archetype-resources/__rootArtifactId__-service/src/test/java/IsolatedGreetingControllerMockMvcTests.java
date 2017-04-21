package ${package};

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ${package}.controller.GreetingController;
import ${package}.entity.Greeting;
import ${package}.service.GreetingService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class IsolatedGreetingControllerMockMvcTests {

  private MockMvc mvc;

  @InjectMocks
  private GreetingController greetingController;

  @Mock
  private GreetingService greetingService;

  Greeting mickey;

  @Before
  public void setUp() {
    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    this.mvc = MockMvcBuilders.standaloneSetup(this.greetingController) //
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()) //
      .setViewResolvers((ViewResolver) (viewName, locale) -> new MappingJackson2JsonView()) //
      .build();

    this.mickey = new Greeting(1L, "mickey", 0L);
  }

  @Test
  public void listGreetings() throws Exception {
    final PageRequest pageable = new PageRequest(0, 20);
    given( //
      this.greetingService.find(pageable) //
    ).willReturn( //
      new PageImpl<>(newArrayList(this.mickey), pageable, 1) //
    );

    this.mvc.perform( //
      get("/api/greetings") //
        .param("page", String.valueOf(pageable.getPageNumber()))
        .param("size", String.valueOf(pageable.getPageSize()))
        .accept(MediaType.APPLICATION_JSON) //
    )
      .andExpect(status().isOk())
      .andExpect(jsonPath("@.content").exists())
      .andExpect(jsonPath("@.content").isArray());
  }
}
