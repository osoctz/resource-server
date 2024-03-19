package cn.metaq.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity(debug = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true) //开启鉴权服务
public class ResourceServerConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        http.authorizeRequests((authorizeRequests) -> authorizeRequests.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer((oauth2ResourceServer) ->
                        oauth2ResourceServer
                                .jwt(Customizer.withDefaults())
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests().anyRequest().authenticated().and()
//                .oauth2ResourceServer().jwt();
//
//        return http.build();
//    }

    //    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();;
//    }
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        String issuerUri = "http://127.0.0.1:9000";
//        return JwtDecoders.fromIssuerLocation(issuerUri);
//    }

}