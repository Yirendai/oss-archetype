package ${package};

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ${package}.controller.GreetingController;
import ${package}.entity.Greeting;
import ${package}.service.GreetingService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Spring REST Docs makes use of snippets produced by tests written with Spring MVC Test or REST Assured.
 * This test-driven approach helps to guarantee the accuracy of your service’s documentation.
 * If a snippet is incorrect the test that produces it will fail.
 * see: http://docs.spring.io/spring-restdocs/docs/current/reference/html5/
 */
@AutoConfigureRestDocs("target/generated-snippets")
@EnableSpringDataWebSupport
@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
public class GreetingControllerDocumentation {

  @TestConfiguration
  static class CustomizationConfiguration implements RestDocsMockMvcConfigurationCustomizer {

    @Override
    public void customize(final MockMvcRestDocumentationConfigurer configurer) {
      configurer.snippets().withTemplateFormat(TemplateFormats.markdown());
    }
  }

  @TestConfiguration
  static class ResultHandlerConfiguration {

    @Bean
    public RestDocumentationResultHandler restDocumentation() {
      return MockMvcRestDocumentation.document("{method-name}");
    }
  }

  @Autowired
  private MockMvc mvc;

  @MockBean
  private GreetingService greetingService;

  Greeting mickey;

  @Before
  public void setUp() {
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
      .andDo(document("list-greetings"));
  }
}
