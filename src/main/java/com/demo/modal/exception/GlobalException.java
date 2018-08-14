package com.demo.modal.exception;

import com.demo.modal.config.SPDQprperties;
import org.springframework.beans.factory.annotation.Autowired;

public class GlobalException extends Exception {


    private String url;

    private Exception e;

    public GlobalException(String url, Exception e) {
        this.url = url;
        this.e = e;
    }

    @Override
    public String getMessage() {
        return "GlobalExcepton[" + "爬取页面" + this.url + "时出现" + e.getMessage() + "异常]";
    }
}
