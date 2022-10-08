package com.yunusAhmet.rentACar.dto;

import com.yunusAhmet.rentACar.core.validator.PasswordMatches;
import com.yunusAhmet.rentACar.core.validator.ValidPassword;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@PasswordMatches
@EqualsAndHashCode
public class CreateCustomerRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    private String email;

    @ValidPassword
    @NotBlank
    private String password;

    @NotBlank
    private String matchingPassword;

}
