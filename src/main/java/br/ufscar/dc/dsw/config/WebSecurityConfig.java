package br.ufscar.dc.dsw.config;

import br.ufscar.dc.dsw.security.UsuarioDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UsuarioDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] publicRoutes = {
                "/home",
                "/Login"
        };

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/images/upload")
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/error", "/login/**", "/js/**").permitAll()
                        .requestMatchers("/css/**", "/webjars/**","/image/**", "/uploads/**").permitAll()
                        .requestMatchers(publicRoutes).permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers("/client/register").hasRole("ADMIN")
                        .requestMatchers("/client/list").hasRole("ADMIN")
                        .requestMatchers("/client/edit").hasRole("ADMIN")
                        .requestMatchers("/client/edit/*").hasRole("ADMIN")
                        .requestMatchers("/client/**").hasRole("CLIENT")

                        .requestMatchers("/store/register").hasRole("ADMIN")
                        .requestMatchers("/store/list").hasRole("ADMIN")
                        .requestMatchers("/store/edit").hasRole("ADMIN")
                        .requestMatchers("/store/edit/*").hasRole("ADMIN")
                        .requestMatchers("/store/**").hasRole("STORE")

                        .requestMatchers("/vehicle/*/offer/register").hasRole("CLIENT")
                        .requestMatchers("/vehicle/*/offer/save").hasRole("CLIENT")
                        .requestMatchers("/vehicle/**").hasAnyRole("STORE", "ADMIN")

                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll());

        return http.build();
    }
}