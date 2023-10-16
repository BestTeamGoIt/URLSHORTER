
package com.bestteam.urlshorter.auth;



import com.bestteam.urlshorter.constants.Role;
import com.bestteam.urlshorter.validator.PasswordConstraint;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String username;
    private String email;
    @PasswordConstraint
    private String password;
    private Role role;
}
