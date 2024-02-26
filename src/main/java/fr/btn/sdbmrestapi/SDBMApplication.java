package fr.btn.sdbmrestapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
@OpenAPIDefinition(info =
@Info(
        title = "API de la SDBM",
        version = "1.0",
        description = "SDBM API",
        contact = @Contact(url = "http://www.afpa.fr/", name = "SDBM", email = "contact@afpa.fr")
)
)
public class SDBMApplication extends Application {

}