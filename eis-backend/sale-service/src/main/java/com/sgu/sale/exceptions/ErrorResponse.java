package com.sgu.sale.exceptions;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @NonNull
    private Integer status;

    // Hiển thị cho User xem
    @NonNull
    private String message;

    // Hiển thị cho Dev xem
    @NonNull
    private String detailMessage;

    private Object error;

    @NonNull
    private Integer code;

    @NonNull
    private String moreInformation;

}
