package fr.btn.sdbmrestapi.security;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.ws.rs.NameBinding;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@SecurityRequirement(name = "Bearer Authentication")
public @interface Tokened {

}