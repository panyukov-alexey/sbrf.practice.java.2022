package sbrf.practice.jsv.list.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
      .authorizeRequests()  
        .anyRequest().authenticated()  
        .and()  
        .formLogin()
        .defaultSuccessUrl("/actuator", true)
        .and()  
        .httpBasic();

      return http.build();
   }

   @Bean
   public InMemoryUserDetailsManager userDetailsService() {
         UserDetails user = User
         .withUsername("user")
         .password("{noop}password")
         .roles("ADMIN")
         .build();
         return new InMemoryUserDetailsManager(user);
   }
}