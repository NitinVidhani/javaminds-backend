package tech.javaminds.javaminds.util;


public class Constants {

    public static final String[] WHITELIST = {
        "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",

                // Api Endpoints
                "/api/roles",
                "/api/categories",
                "/api/categories/**",
                "/api/posts",
                "/api/posts/*"
    };

}
