package com.expensetracker.expensetracker.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UserRegister {

    @Length(min = 3, max = 15, message = "identifier length should be between 3 and 15")
    @NotBlank(message = "Identifier is mandatory")
    private String identifier;

    @NotBlank(message = "First Name is mandatory")
    @Size(max = 15, message = "Max length allower 15")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    @Size(max = 15, message = "Max length allowed 15")
    private String lastName;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotBlank(message = "Email is mandatory")
    @Size(max = 120, message = "email size too long")
    private String email;

    @Size(min = 5, max = 12,message="Password should be between 5 and 12")
    @NotBlank(message = "Password is mandatory")
    private String password;

}
