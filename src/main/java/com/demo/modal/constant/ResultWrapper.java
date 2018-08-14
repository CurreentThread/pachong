package com.demo.modal.constant;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultWrapper {
    private Integer code;

    private String msg;

    private Object obj;


}
