package pl.dabal.accountaggregator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "consents")
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

    @OneToMany(mappedBy = "consent", fetch = FetchType.EAGER, cascade = REMOVE)
    private List<Account> accounts;

    private LocalDateTime createdDate;
    private LocalDateTime scopeTimeLimit;

@Size(max=1024)
    private String accessToken;



public void setCreatedDate(){
        this.createdDate=LocalDateTime.now();
    }

    public void setScopeTimeLimit(int limitInDays){
        this.scopeTimeLimit=this.createdDate.plusDays(limitInDays);
    }

    public Consent(User user, String name, String state, int scopeLimitInDays )
    {
        this.user=user;
        this.name=name;
        this.state=state;
        this.setCreatedDate();
        this.setScopeTimeLimit(scopeLimitInDays);
    }

}
