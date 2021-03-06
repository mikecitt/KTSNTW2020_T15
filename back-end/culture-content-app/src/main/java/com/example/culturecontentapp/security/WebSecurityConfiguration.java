package com.example.culturecontentapp.security;

import com.example.culturecontentapp.security.jwt.TokenAuthenticationFilter;
import com.example.culturecontentapp.service.AccountDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AccountDetailsService accountDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public WebSecurityConfiguration(AccountDetailsService accountDetailsService,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.accountDetailsService = accountDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PUT",
            "DELETE");
      }
    };
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auth/activate", "/api/auth/activate/**", "/api/auth/resend", "/api/review",
            "/api/cultural-offer/", "/api/cultural-offer/**", "/api/news/**", "/api/news/culturalOffer/**",
            "/api/review", "/api/sub-types", "/api/sub-types/**", "/api/types", "/api/types/**", "/uploads/*")
        .permitAll().anyRequest().authenticated().and()
        .addFilterBefore(new TokenAuthenticationFilter(accountDetailsService), BasicAuthenticationFilter.class).cors()
        .and().csrf().disable();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(accountDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }
}
