package com.demo.modal.constant;

public enum SystemEnum {
    ERROR(0, "爬取出现错误异常"),
    SUCCESS(1, "爬取成功");


    private Integer code;

    private String detail;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    SystemEnum(Integer code, String detail) {
        this.code = code;
        this.detail = detail;
    }
}
