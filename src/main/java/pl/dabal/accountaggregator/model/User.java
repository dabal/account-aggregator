package pl.dabal.accountaggregator.model;

import lombok.*;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static javax.persistence.CascadeType.REMOVE;

@Builder
@Setter
@Getter
//@RequiredArgsConstructor
@ToString
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min=1,max=50)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min=1,max=50)
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String token;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,cascade=REMOVE)
   private List<Consent> consents;



    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}