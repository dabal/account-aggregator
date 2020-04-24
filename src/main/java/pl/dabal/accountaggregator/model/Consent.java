package pl.dabal.accountaggregator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

@Entity
@Table(name="consents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Consent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String state;

    @JsonIgnore
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "consent", fetch = FetchType.EAGER,cascade=REMOVE)
    private List<Account> accounts;

}
