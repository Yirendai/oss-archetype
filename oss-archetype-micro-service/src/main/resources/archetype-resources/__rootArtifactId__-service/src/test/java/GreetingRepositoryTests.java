package ${package};

import static org.assertj.core.api.Assertions.assertThat;

import ${package}.entity.Greeting;
import ${package}.repository.GreetingRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED) // Non transactional (disable transaction management)
public class GreetingRepositoryTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private GreetingRepository repository;

  @Test
  public void testCantFindMickey() throws Exception {
    this.entityManager.persist(Greeting.greetingBuilder().toWhom("mickey").build());
    final Greeting greeting = this.repository.findByToWhom("mickey");
    assertThat(greeting.getToWhom()).isEqualTo("mickey");
  }
}
