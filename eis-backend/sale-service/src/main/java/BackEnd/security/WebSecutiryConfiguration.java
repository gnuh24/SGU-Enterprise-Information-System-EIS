package BackEnd.security;

import BackEnd.exceptions.AuthException.AuthExceptionHandler;
import BackEnd.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecutiryConfiguration {

    @Autowired
    @Lazy
    private AccountService accountService;

    @Autowired
    @Lazy
    private AuthExceptionHandler authExceptionHandler;

    @Autowired
    private JwtTokenFilter jwtAuthFIlter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource) throws Exception {

        http
                // Loại bỏ bảo vệ CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // Configure các luồng truy cập
                .authorizeHttpRequests((auth) -> auth

                        // TODO: CÁC API LIÊN QUAN ĐẾN PRODUCT
                        .requestMatchers(HttpMethod.GET, "/api/redis/get/name")                                         .permitAll()

                        .requestMatchers(HttpMethod.GET, "/accounts/{Id}")                                                .permitAll()
                        .requestMatchers(HttpMethod.GET, "/accounts/email")                                             .permitAll()
                        .requestMatchers(HttpMethod.GET, "/accounts")                                                      .hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/accounts/check-email")                                  .permitAll()

                        .requestMatchers(HttpMethod.POST, "/accounts")                                                    .permitAll()
                        .requestMatchers(HttpMethod.POST, "/accounts/activate-account")                         .permitAll()
                        .requestMatchers(HttpMethod.POST, "/accounts/{accountId}/account-activity-logs").hasAnyAuthority("USER")


                        .requestMatchers(HttpMethod.PATCH, "/accounts/{id}")                                            .hasAnyAuthority("USER")
                        .requestMatchers(HttpMethod.PATCH, "/accounts/{id}/update-password")                .hasAnyAuthority("USER")
                        .requestMatchers(HttpMethod.PATCH, "/accounts/{id}/update-email")                       .hasAnyAuthority("USER")


                        .requestMatchers( HttpMethod.GET, "/media")                                                           .permitAll()
                        .requestMatchers( HttpMethod.POST, "/media/upload")                                             .permitAll()

                        .requestMatchers(HttpMethod.POST, "/auth/user/login")                                            .permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/staff/login")                                            .permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/send-otp-update-email")                        .hasAnyAuthority("USER")
                        .requestMatchers(HttpMethod.POST, "/auth/send-otp-reset-password")                    .permitAll()

                        .requestMatchers(HttpMethod.POST, "/auth/refresh-token")                                       .permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/auth/{id}/update-role")                                 .hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/auth/{id}/update-status")                              .hasAnyAuthority("ADMIN")


                        // Xác thực tất cả các request
                        .anyRequest()
                        .authenticated()

                ).httpBasic(Customizer.withDefaults())

                // Add JWT vào chuỗi lọc và ưu tiên loc theo JWT
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtAuthFIlter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling((exceptionHandling) -> exceptionHandling

                        // Cấu hình xử lý ngoại lệ cho trường hợp không xác thực (Login sai ^^)
                        .authenticationEntryPoint(authExceptionHandler)

                        // Cấu hình xử lý ngoại lệ cho trường hợp truy cập bị từ chối (Không đủ quyền)
                        .accessDeniedHandler(authExceptionHandler)

                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(accountService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    //    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//            throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

}
