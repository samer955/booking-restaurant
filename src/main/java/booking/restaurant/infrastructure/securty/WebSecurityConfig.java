package booking.restaurant.infrastructure.securty;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication()
              .withUser("admin")
              .password("admin")
              .roles("ADMIN");
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/**/admin/**")
              .authenticated()
              .and().formLogin();
  }


  @Bean
  PasswordEncoder encoder() {
      // Das ist nur zum Ausprobieren gedacht!
      // Für Produktion ist das ungeeignet!
      return NoOpPasswordEncoder.getInstance();
  }
}