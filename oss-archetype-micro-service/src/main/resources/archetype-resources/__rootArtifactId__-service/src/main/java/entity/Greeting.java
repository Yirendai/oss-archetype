package ${package}.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
@Builder(builderMethodName = "greetingBuilder")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Greeting implements Serializable {

  private static final long serialVersionUID = -1;

  @Id
  @GeneratedValue
  private Long id;

  @Length(max = 20, message = "length must less than 20")
  @Column(nullable = false, length = 20, unique = true)
  private String toWhom;

  //@org.springframework.data.annotation.Version
  @Version
  private Long version;
}
