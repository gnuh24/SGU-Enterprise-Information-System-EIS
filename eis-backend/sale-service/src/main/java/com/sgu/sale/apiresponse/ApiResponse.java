package com.sgu.sale.apiresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;       // HTTP status code
    private String message;   // User-friendly message
    private T data;           // Actual response data
}
