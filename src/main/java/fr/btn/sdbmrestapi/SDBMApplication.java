package fr.btn.sdbmrestapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@OpenAPIDefinition(info =
@Info(
        title = "API de la SDBM",
        version = "1.0",
        description = "SDBM API",
        contact = @Contact(url = "http://www.afpa.fr/", name = "SDBM", email = "contact@afpa.fr")
),
        servers = @Server(
                url = "http://localhost:8080/"
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@ApplicationPath("api")
public class SDBMApplication extends Application {

}