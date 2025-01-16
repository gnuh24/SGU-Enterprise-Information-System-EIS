package BackEnd.Configure.WebSecurity;

import BackEnd.Configure.ErrorResponse.AuthException.AuthExceptionHandler;
import BackEnd.Configure.ErrorResponse.TheValueAlreadyExists;
import BackEnd.Entity.AccountEntity.Account;
import BackEnd.Service.AccountServices.AccountService.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private IAccountService accountService;

    @Autowired
    @Lazy
    private AuthExceptionHandler authExceptionHandler;

    @Autowired
    private JWTAuthorizationFilter jwtAuthFIlter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource) throws Exception {

        http
                // Loại bỏ bảo vệ CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // Configure các luồng truy cập
                .authorizeHttpRequests((auth) -> auth

                        // TODO: CÁC API LIÊN QUAN ĐẾN PRODUCT
                        // Các API `Brand`
                        .requestMatchers(HttpMethod.GET, "/Brand/noPaging")                             .permitAll()
                        .requestMatchers(HttpMethod.GET, "/Brand")                                      .permitAll()
                        .requestMatchers(HttpMethod.GET, "/Brand/{brandId}")                            .permitAll()
                        .requestMatchers(HttpMethod.POST, "/Brand")                                     .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.PATCH, "/Brand")                                    .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.DELETE, "/Brand/{brandId}")                         .hasAnyAuthority("Manager")

                        // Các API `Category`
                        .requestMatchers(HttpMethod.GET, "/Category/noPaging")                          .permitAll()
                        .requestMatchers(HttpMethod.GET, "/Category")                                   .permitAll()
                        .requestMatchers(HttpMethod.GET, "/Category/{categoryId}")                      .permitAll()
                        .requestMatchers(HttpMethod.POST, "/Category")                                  .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.PATCH, "/Category")                                 .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.DELETE, "/Category/{categoryId}")                   .hasAnyAuthority("Manager")


                        // Các API `Product`
                        .requestMatchers(HttpMethod.GET, "/Product/Admin")                              .hasAnyAuthority("Manager", "Employee")
                        .requestMatchers(HttpMethod.GET, "/Product/Admin/{shoeId}")                     .hasAnyAuthority("Manager", "Employee")
                        .requestMatchers(HttpMethod.GET, "/Product/CommonUser")                         .permitAll()
                        .requestMatchers(HttpMethod.GET, "/Product/CommonUser/{shoeId}")                .permitAll()
                        .requestMatchers(HttpMethod.POST, "/Product")                                   .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.PATCH, "/Product")                                  .hasAnyAuthority("Manager")



                    // TODO: Các API liên quan đến `Account`

                        .requestMatchers(HttpMethod.POST, "/Auth/SignIn")                               .permitAll()
                        .requestMatchers(HttpMethod.POST, "/Auth/LoginAdmin")                           .permitAll()
                        .requestMatchers(HttpMethod.POST, "/Auth/Registration")                         .permitAll()
                        .requestMatchers(HttpMethod.POST, "/Auth/Refresh")                              .permitAll()
                        .requestMatchers(HttpMethod.GET, "/Auth/ActiveUser")                            .permitAll()

                        .requestMatchers(HttpMethod.GET, "/Account/isThisEmailExists")                  .permitAll()
                        .requestMatchers(HttpMethod.GET, "/Account")                                    .hasAnyAuthority("Admin")
                        .requestMatchers(HttpMethod.GET, "/Account/{accountId}")                        .hasAnyAuthority("User", "Employee", "Manager", "Admin")

                        .requestMatchers(HttpMethod.PATCH, "/Account/ChangeStatus")                     .hasAnyAuthority("Admin")
                        .requestMatchers(HttpMethod.PATCH, "/Account/ChangeRole")                       .hasAnyAuthority("Admin")

                        .requestMatchers(HttpMethod.PATCH, "/Account/UpdateInformation")                .hasAnyAuthority("User", "Employee", "Manager", "Admin")

                        // TODO: Các API liên quan đến chức năng mua hàng

                        // Các API Giỏ hàng
                        .requestMatchers(HttpMethod.GET, "/CartItem/{accountId}")                       .permitAll()
                        .requestMatchers(HttpMethod.POST, "/CartItem")                                  .permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/CartItem")                                 .permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/CartItem")                                .permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/CartItem/{accountId}")                    .permitAll()

                        // Các API Đơn hàng
                        .requestMatchers(HttpMethod.GET, "/pay")                                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/Order/Admin")                                .hasAnyAuthority("Manager", "Employee")
                        .requestMatchers(HttpMethod.GET, "/Order/Admin/{id}")                           .hasAnyAuthority("Manager", "Employee")
                        .requestMatchers(HttpMethod.GET, "/Order/MyOrder")                              .hasAnyAuthority("User", "Admin")
                        .requestMatchers(HttpMethod.GET, "/Order/MyOrder/{id}")                         .hasAnyAuthority("User", "Admin")

                        .requestMatchers(HttpMethod.POST, "/Order/User")                                .hasAnyAuthority("User")

                        // Các API Trạng thái đơn hàng
                        .requestMatchers(HttpMethod.POST, "/OrderStatus/Admin")                         .hasAnyAuthority("Manager", "Employee")
                        .requestMatchers(HttpMethod.POST, "/OrderStatus/User")                          .hasAnyAuthority("User")


                        // TODO: API liên quan đến Inventory (nhập kho)
                        .requestMatchers(HttpMethod.GET, "/InventoryReport")                            .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.GET, "/InventoryReport/{id}")                       .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.POST, "/InventoryReport")                           .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.PATCH, "/InventoryReport")                          .hasAnyAuthority("Manager")

                        // TODO: API Thống kê
                        .requestMatchers(HttpMethod.GET, "/Statistic/BestSeller")                       .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.GET, "/Statistic/OrderStatus")                      .hasAnyAuthority("Manager")
                        .requestMatchers(HttpMethod.GET, "/Statistic/IncomeSummary")                    .hasAnyAuthority("Manager")


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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(accountService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}
