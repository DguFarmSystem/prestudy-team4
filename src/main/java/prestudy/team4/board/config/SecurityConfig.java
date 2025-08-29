package prestudy.team4.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth
                    // GET 요청은 모두 허용 (게시글 조회는 누구나 ok)
                    .requestMatchers(HttpMethod.GET, "/api/v1/posts/**", "/s3/get-url").permitAll()
                    // POST, PUT, DELETE 작업은 인증된 사용자만 OK
                    .requestMatchers("/api/v1/posts/**", "/s3/put-url").authenticated()
                    // 그외 작업들은 모두 일단 허용
                    .requestMatchers("/api/v1/**").permitAll()
                    .anyRequest().permitAll()
            )
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
