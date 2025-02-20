//package com.sgu.sale.exceptions.AuthException;
//
//import java.io.IOException;
//import com.sgu.sale.exceptions.ErrorResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//
//@Component
//
//@Slf4j
//public class AuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {
//
//    @Autowired
//    private MessageSource messageSource;
//
//    // Spring Security
//    // 401 unauthorized
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException authException) throws IOException {
//
//        String message = authException.getLocalizedMessage();
//
//        String detailMessage = String.valueOf(authException.fillInStackTrace());
//
//        int code = 8;
//        String moreInformation = "http://localhost:8080/api/v1/exception/8";
//
//        ErrorResponse errorResponse = new ErrorResponse(401, message, detailMessage, null, code, moreInformation);
//
//        // Convert object to JSON
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(errorResponse);
//
//        // Set the response status code
//        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401 Unauthorized
//
//        // Set content type and write JSON response
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(json);
//    }
//
//
//    // Spring Security
//    // 403 Forbidden
//    @Override
//    public void handle(HttpServletRequest request,
//                       HttpServletResponse response,
//                       AccessDeniedException exception) throws IOException {
//
//        String message = "Bạn không có đủ quyền để thực hiện chức năng này !!"; // Access denied message
//        String detailMessage = exception.getLocalizedMessage(); // Detailed message from exception
//        int code = 9; // Custom error code
//        String moreInformation = "http://localhost:8080/api/v1/exception/9"; // URL for more information
//
//        ErrorResponse errorResponse = new ErrorResponse(403, message, detailMessage, null, code, moreInformation);
//
//        // Convert object to JSON
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(errorResponse);
//
//        // Set response content type and write JSON response
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpStatus.FORBIDDEN.value()); // 403 Forbidden
//        response.getWriter().write(json);
//    }
//
//
//}