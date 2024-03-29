package com.final_project.daily_operations.configuration;

import com.final_project.daily_operations.filter.CustomAuthenticationFilter;
import com.final_project.daily_operations.filter.CustomAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "SELECT username, password, enabled from customer where username = ?")
                .authoritiesByUsernameQuery(
                        "SELECT u.username, a.authority " +
                                "FROM  customer u LEFT JOIN authority a ON u.authority_id = a.id " +
                                "WHERE u.username = ? "
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/customer/login");
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.POST,
                        "customer/login/**",
                        "/customer/save").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/news/**",
                        "/currency_rates",
                        "/currency_rates/chart_rates",
                        "/currency/code/**",
                        "/currency/id/**",
                        "/currency",
                        "/customer/forgot/**",
                        "/customer/userWithToken",
                        "/customer/login/**",
                        "/customer/recovery/**"
                        ).permitAll()
                .antMatchers(HttpMethod.GET,
                        "/currency_rates/update",
                        "/balance/all",
                        "/customer/all",
                        "/customer/search/**",
                        "/customer/delete/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,
                        "/customer/valid",
                        "customer/total_balance",
                        "/balance/my",
                        "/transactions",
                        "/customer/get",
                        "/transactions",
                        "/transactions/all").hasAnyAuthority("ADMIN", "CLIENT")
                .antMatchers(HttpMethod.POST,
                        "/balance/add/**",
                        "/transactions").hasAnyAuthority("ADMIN", "CLIENT")
                .anyRequest()
                .authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable();


        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://localhost:8081","http://loan:8081"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "access_token", "refresh_token"));
        configuration.setExposedHeaders(Arrays.asList("access_token", "refresh_token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}