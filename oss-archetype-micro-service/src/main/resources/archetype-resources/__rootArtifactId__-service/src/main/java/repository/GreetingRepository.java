package ${package}.repository;

import ${package}.entity.Greeting;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface GreetingRepository extends PagingAndSortingRepository<Greeting, Long> {

  Greeting findByToWhom(String toWhom);
}
