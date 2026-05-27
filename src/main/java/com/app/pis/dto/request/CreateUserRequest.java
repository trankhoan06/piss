package com.app.pis.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateUserRequest(
        @NotBlank(message = "First name is required")
        @JsonProperty("first_name")
        String firstName,

        @NotBlank(message = "Last name is required")
        @JsonProperty("last_name")
        String lastName,

        @NotNull(message = "Birth day is required")
        @Past(message = "Birth day must be in the past") // impelemen thêm >= 18 tuổi
        @JsonProperty("birth_day")
        LocalDate birthDay,

        @Size(max = 255, message = "Address too long")
        @JsonProperty("address")
        String address,

        @NotBlank(message = "Phone is required")
        @Pattern(
                regexp = "^(0[0-9]{9,10})$",
                message = "Phone must be valid Vietnamese format"
        )
        @JsonProperty("phone")
        String phone,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 100)
        @JsonProperty("email")
        String email,

        @NotBlank(message = "CCCD is required")
        @Pattern(
                regexp = "^[0-9]{12}$",
                message = "CCCD must be 12 digits"
        )
        @JsonProperty("cccd")
        String CCCD

      /*  @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100, message = "Password must be 6-100 characters")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "Password must contain letters and numbers"
        )
        @JsonProperty("password")
        String password,*/

     /*   @NotBlank(message = "Role is required")
        @Pattern(
                regexp = "^(admin|staff|user)$",
                message = "Role must be admin, staff or user"
        )
        @JsonProperty("role")
        String role*/
) {}