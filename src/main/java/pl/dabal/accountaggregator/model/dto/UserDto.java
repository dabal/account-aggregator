package pl.dabal.accountaggregator.model.dto;

import lombok.*;
import pl.dabal.accountaggregator.validators.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 4, max = 15)
    private String password;

}
