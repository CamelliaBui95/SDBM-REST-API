package fr.btn.sdbmrestapi.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String login;
    private String password;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String role;
}
