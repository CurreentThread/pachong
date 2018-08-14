package com.demo.common;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CommonUtil {

    //判断数组是否为空

    public static <T> boolean isEmptyArray(List<T> list) {

        boolean isEmpty = false;
        if (list == null)
            return true;

        if (list.size() == 0)
            isEmpty = true;
        if (list.size() != 0 && list.get(0) == null)
            isEmpty = true;
        return isEmpty;

    }


    public static boolean isQuaterPage(Integer page, Integer totalPage) {
        return page <= (totalPage >> 2) ? true : false;
    }


}
