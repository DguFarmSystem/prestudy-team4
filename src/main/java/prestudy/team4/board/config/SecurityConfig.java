package prestudy.team4.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/api/v1/auth/login","/api/v1/auth/join").permitAll()
                        .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated()
                );

        http
                .formLogin((auth)-> auth.loginPage("/api/v1/auth/login")
                        .loginProcessingUrl("/api/v1/auth/loginProc")
                        .permitAll()
                );

        http
                .csrf((auth)->auth.disable());

        http
                .sessionManagement(
                        (auth) -> auth
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .sessionFixation((sessionFixation)->sessionFixation.newSession())
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                );

        http
                .logout((auth)->auth.logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }

}