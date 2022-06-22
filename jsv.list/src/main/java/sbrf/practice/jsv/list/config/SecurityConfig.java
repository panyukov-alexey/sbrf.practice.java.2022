package sbrf.practice.jsv.list.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void initialize(AuthenticationManagerBuilder builder, DataSource dataSource) throws Exception {
        builder.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username,password,'true' as enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM users WHERE username=?")
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

}