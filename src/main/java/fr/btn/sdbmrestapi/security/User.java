package fr.btn.sdbmrestapi.security;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String login;
    private String password;
    private String email;
    private String role;
}
