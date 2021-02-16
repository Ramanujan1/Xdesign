package com.xdesign.util;

import com.xdesign.model.ExcelData;
import com.xdesign.model.ExcelDataVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public enum Order {
        ASC,
        DSC
    }

    public enum HillCategory {
        MUN,
        TOP,
    }

    public static  List<ExcelDataVO> mapEntitytoVO(List<ExcelData> excelDataList) {
        List<ExcelDataVO> excelDataVOList = new ArrayList<>();
        ExcelDataVO excelDataVO;
        for ( ExcelData excelData : excelDataList) {
            excelDataVO = new ExcelDataVO();

             BeanUtils.copyProperties(excelData, excelDataVO);
            excelDataVOList.add(excelDataVO);
        }
        return excelDataVOList;
    }


    public static boolean checkValueExistsInOrderEnum(Util.Order nameOrder){
        boolean validOrderName = false;

        for (Util.Order order : Util.Order.values()) {
            if (order.name().equals(nameOrder.name())) {
                validOrderName = true;
            }
        }
        return validOrderName;
    }

}
