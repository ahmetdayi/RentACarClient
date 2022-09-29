package com.yunusAhmet.rentACar.dto;

import com.yunusAhmet.rentACar.core.validator.PasswordMatches;
import com.yunusAhmet.rentACar.core.validator.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@PasswordMatches(message ="Password dont match")
@Data
public class UpdateCustomerRequest {

    @NotNull
    private int customerId;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Email(regexp = ".+@.+\\..+")
    @NotBlank
    private String email;

    @ValidPassword
    @NotBlank
    private String password;

    @NotBlank
    private String matchingPassword;
}
