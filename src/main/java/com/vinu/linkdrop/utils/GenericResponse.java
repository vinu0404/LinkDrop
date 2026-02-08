package com.vinu.linkdrop.utils;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {

    private String message;
    private String status;
    private T data;
}
