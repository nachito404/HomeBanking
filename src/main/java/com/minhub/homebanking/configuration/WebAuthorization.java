package com.minhub.homebanking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //cuidado con el **, si hay algo debajo que sigue la misma ruta y es mas especifico lo va a pisar, se vuelve obsoleto.
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/clients","/api/login").permitAll()
                .antMatchers("/web/index.html","/web/img/**","/web/js/index.js","/web/css/style.css","/web/accounts.html","/web/js/accounts.js","/favicon.ico").permitAll()
                .antMatchers("/rest/**","/h2-console/**").hasAuthority("ADMIN")
                .antMatchers("/api/clients/current","/api/accounts/{id}","/api/clients","/web/cards.html","/web/js/cards.js","/web/css/cards.css","/api/clients/current/accounts","/web/cards.html","/web/create-cards.html","/web/js/create-cards.js").hasAnyAuthority("CLIENT","ADMIN")
                .anyRequest().denyAll();

        http.formLogin().usernameParameter("email").passwordParameter("password").loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        http.headers().frameOptions().disable();

        http.csrf().disable();

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}