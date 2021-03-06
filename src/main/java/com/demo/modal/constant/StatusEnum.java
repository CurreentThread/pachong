package com.demo.modal.constant;

public enum StatusEnum {

    /*
      状态：1-全部2-连载3-完结4-未开播
      类目：1全部2-电视剧3-电影4-动漫5-综艺
      地区：1-全部2-大陆3-台湾4-美国-5韩国6-日本7-英国8-其他
      年份：1-全部2-2018 3-2017 4-2016 5-2015-2010 6-2010-2000 7-90年代 8更早
    *
    *
    *
    * */


    ALL_SATUS(0),
    CONTINUE(1),
    OVER(2),
    NO_OPEN(3);

    private Integer status;

    private StatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
