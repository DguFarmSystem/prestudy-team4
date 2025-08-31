package prestudy.team4.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/join").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/**", "/s3/get-url").permitAll()
                        .requestMatchers("/api/v1/posts/**", "/s3/put-url").authenticated()
                        .requestMatchers("/", "/user/**", "/my/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(auth -> auth
                        .loginPage("/api/v1/auth/login")
                        .loginProcessingUrl("/api/v1/auth/loginProc")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(auth -> auth
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation(sessionFixation -> sessionFixation.newSession())
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )
                .logout(auth -> auth
                        .logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
