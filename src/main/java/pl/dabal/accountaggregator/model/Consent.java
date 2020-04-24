package pl.dabal.accountaggregator.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "consent", fetch = FetchType.EAGER)
    private List<Account> accounts;

}
