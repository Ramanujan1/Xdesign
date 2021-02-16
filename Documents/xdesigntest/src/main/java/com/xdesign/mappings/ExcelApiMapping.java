package com.xdesign.mappings;

import com.fasterxml.jackson.annotation.JsonView;
import com.xdesign.exception.InvalidQueryException;
import com.xdesign.model.ExcelData;
import com.xdesign.model.ExcelDataVO;
import com.xdesign.model.Views;
import com.xdesign.service.ExcelServiceImpl;
import com.xdesign.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ExcelApiMapping {

    @Autowired
    public ExcelServiceImpl getExcelService;

    @Inject
    @Qualifier("testexcel")
    private List<ExcelData> excelData;


    Comparator<ExcelData> compareByHeightAndName = Comparator
            .comparing(ExcelData::getHeightM)
            .thenComparing(ExcelData::getName);

    Comparator<ExcelData> compareByHeightReverseAndName = Comparator
            .comparing(ExcelData::getHeightM).reversed()
            .thenComparing(ExcelData::getName);


    Comparator<ExcelData> compareByHeightAndNameReverse = Comparator
            .comparing(ExcelData::getHeightM)
            .thenComparing(ExcelData::getName).reversed();

    Comparator<ExcelData> compareByHeightReverseAndNameReverse = Comparator
            .comparing(ExcelData::getHeightM).reversed()
            .thenComparing(ExcelData::getName).reversed();

    Comparator<ExcelData> compareByHeightReverse = Comparator
            .comparing(ExcelData::getHeightM).reversed();

    Comparator<ExcelData> compareByHeight = Comparator
            .comparing(ExcelData::getHeightM);

    Comparator<ExcelData> compareByNameReverse = Comparator
            .comparing(ExcelData::getName).reversed();

    Comparator<ExcelData> compareByName = Comparator
            .comparing(ExcelData::getName);

    @GetMapping("/filterByHillCategory")
    public @JsonView(Views.Create.class) List<ExcelDataVO> getExcelData(@RequestParam Optional<String> hillCategory) throws InvalidQueryException {

        List<ExcelData> excelDataHillCat = excelData.stream().filter(invoice ->  invoice.getPost1997() != null && invoice.getPost1997().equals(hillCategory)).collect(Collectors.toList());

        boolean validCatergory = false;
        for (Util.HillCategory hillCategoryEnum : Util.HillCategory.values()) {
            if (hillCategoryEnum.name().equals(hillCategory.get())) {

                validCatergory = true;
            }
        }

        if(!validCatergory) {
            throw new InvalidQueryException("invalid hillCategory");
        }

        return Util.mapEntitytoVO(excelDataHillCat);
    }


    @GetMapping("/getDataLimitCount")
    public List<ExcelDataVO> getExcelDataByCount(@RequestParam Optional<Integer> count) throws InvalidQueryException{

      if ( count.isPresent() && count.get() >= 0 ) {
          return Util.mapEntitytoVO(excelData.stream().limit(count.get()).collect(Collectors.toList()));
      }

      if ( count.isPresent() && count.get() < 0 ) {
          throw new InvalidQueryException("invalid count : should be greater than 0");
      }

      return  Util.mapEntitytoVO(excelData);
    }


    @GetMapping("/sortByHeightName")
    public List<ExcelDataVO> getSortByHeightName(@RequestParam Optional<Util.Order>  heightOrder, @RequestParam Optional<Util.Order> nameOrder, @RequestParam Optional<Double> maxHeigh, @RequestParam Optional<Double> minHeigh) throws InvalidQueryException {

        List<ExcelData> sortedExcelData = new ArrayList<>();
        boolean validOrderHeight = false;
        boolean validOrderName = false;

        if (heightOrder.isPresent()) {
             validOrderHeight = Util.checkValueExistsInOrderEnum(heightOrder.get());
        }

        if (nameOrder.isPresent()) {
             validOrderName = Util.checkValueExistsInOrderEnum(nameOrder.get());
        }


        if ((minHeigh.isPresent() && minHeigh.get() < 0) ||  (maxHeigh.isPresent() && maxHeigh.get() < 0)) {
            throw new InvalidQueryException("invalid (max/min) height value");
        }

        if ( !validOrderHeight )
        {
            throw new InvalidQueryException("invalid order height");
        }

        if ( !validOrderName )
        {
            throw new InvalidQueryException("invalid order name");
        }

        if (!heightOrder.isPresent() && nameOrder.isPresent()) {

            if (nameOrder.get().equals(Util.Order.DSC)) {
                sortedExcelData = excelData.stream().filter(invoice -> invoice.getName() !=null)
                        .sorted(compareByNameReverse)
                        .collect(Collectors.toList());
            } else {
                sortedExcelData = excelData.stream().filter(invoice -> invoice.getName() !=null)
                        .sorted(compareByName)
                        .collect(Collectors.toList());
            }

        } else  if (heightOrder.isPresent()  && !nameOrder.isPresent()) {

            if (heightOrder.get().equals(Util.Order.DSC)) {
                sortedExcelData = excelData.stream().filter(invoice -> invoice.getHeightM() !=null)
                        .sorted(compareByHeightReverse)
                        .collect(Collectors.toList());
            } else {
                sortedExcelData = excelData.stream().filter(invoice -> invoice.getHeightM() !=null)
                        .sorted(compareByHeight)
                        .collect(Collectors.toList());
            }

        } else if (heightOrder.get().equals(Util.Order.ASC) && nameOrder.get().equals(Util.Order.ASC)) {
            sortedExcelData = excelData.stream().filter(invoice -> invoice.getHeightM() !=null)
                    .sorted(compareByHeightAndName)
                    .collect(Collectors.toList());
        } else if (heightOrder.get().equals(Util.Order.DSC) && nameOrder.get().equals(Util.Order.ASC)) {
            sortedExcelData = excelData.stream().filter(invoice -> invoice.getHeightM() !=null)
                    .sorted(compareByHeightReverseAndName)
                    .collect(Collectors.toList());
        } else if (heightOrder.get().equals(Util.Order.ASC) && nameOrder.get().equals(Util.Order.DSC)) {
            sortedExcelData = excelData.stream().filter(invoice -> invoice.getHeightM() !=null)
                    .sorted(compareByHeightAndNameReverse)
                    .collect(Collectors.toList());
        } else {
            sortedExcelData = excelData.stream().filter(invoice -> invoice.getHeightM() !=null)
                    .sorted(compareByHeightReverseAndNameReverse)
                    .collect(Collectors.toList());
        }

        if (maxHeigh.isPresent()) {
            sortedExcelData = sortedExcelData.stream().filter(invoice -> invoice.getHeightM().compareTo(maxHeigh.get()) < 0).collect(Collectors.toList());
        }

        if (minHeigh.isPresent()) {
            sortedExcelData = sortedExcelData.stream().filter(invoice -> invoice.getHeightM().compareTo(minHeigh.get()) > 0).collect(Collectors.toList());
        }

        return Util.mapEntitytoVO(sortedExcelData);
    }

}