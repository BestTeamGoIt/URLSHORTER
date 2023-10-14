
package com.bestteam.urlshorter.models;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.time.ZoneOffset;

@Component
@Configuration
public class Constants {
    //============================== PROPERTY KEYS =======================
    public static final String ADMIN_USERNAME = "gcp.vars.admin.user";
    public static final String ADMIN_PASSWORD = "gcp.vars.admin.password";
    public static final String GOOGLE_CLIENT_ID = "spring.security.oauth2.client.registration.google.clientId";
    public static final String GOOGLE_CLIENT_SECRET = "spring.security.oauth2.client.registration.google.clientSecret";
    public static final ZoneOffset TIME_ZONE = ZoneOffset.of("+00:00");


}
