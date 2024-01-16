package com.suma.consumer.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ResponseDto {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int status;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Object data;

    public ResponseDto() {
    }

    public ResponseDto(String message) {
        this.message = message;
    }

    public ResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseDto(int status, String message, Object data) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

}
