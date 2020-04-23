package pl.dabal.accountaggregator.model.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@ToString
@Setter
@Getter
@AllArgsConstructor
public class CredentialsDto {

    @NotNull
    @NotBlank
    @Email
    private String Email;

    @NotNull
    @NotBlank
    private String Password;
}
