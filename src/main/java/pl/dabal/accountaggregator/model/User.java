package pl.dabal.accountaggregator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @NotNull
    @UniqueElements
    @Email
    @Size(min=3)
    private String username;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-z][a-z|A-Z]{1,5}\\d+[a-z|A-Z|\\d]+$")
    private String password;

}
