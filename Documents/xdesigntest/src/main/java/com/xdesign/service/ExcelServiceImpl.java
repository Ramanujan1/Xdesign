package com.xdesign.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xdesign.model.ExcelData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.poiji.bind.Poiji;

@Component
public class ExcelServiceImpl implements ExcelPoijiService{

    List<ExcelData> excelData = new ArrayList<>();

    @Autowired
    ExcelServiceImpl() {}

    @Value("${filePath}")
    public String FILE_PATH;


    @Bean(name = "testexcel")
    public List<ExcelData> getListfromExcelData() {
        File file = new File(FILE_PATH);
        excelData = Poiji.fromExcel(file, ExcelData.class);
        return excelData;
    }
}