package pl.dabal.accountaggregator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String accountNumber;
@JsonIgnore
    @ManyToOne
    private Consent consent;

}
