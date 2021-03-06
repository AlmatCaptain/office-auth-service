package kz.iitu.office.auth.service.config;

import kz.iitu.office.auth.service.service.EmployeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/v2/api-docs",
                   "/configuration/ui",
                   "/swagger-resources/**",
                   "/configuration/security",
                   "/swagger-ui.html",
                   "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .ignoringAntMatchers("/actuator/hystrix.stream")
            .disable()
            .authorizeRequests()
            .antMatchers("/api/employees/registration")
            .not()
            .fullyAuthenticated()
            .antMatchers("/auth/**")
            .permitAll()
            .antMatchers("/api/reserves/**")
            .permitAll()
            .antMatchers("/api/rooms/**")
            .permitAll()
            .antMatchers("/api/admin/**")
            .hasAuthority("ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .logout()
            .permitAll()
            .and()
            .httpBasic()
            .and()
            .addFilter(new JwtTokenGeneratorFilter(authenticationManager()))
        ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT","PATCH"));
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeInfoService)
            .passwordEncoder(passwordEncoder());
    }
}
