package mappings;

import com.xdesign.exception.InvalidQueryException;
import com.xdesign.mappings.ExcelApiMapping;
import com.xdesign.model.ExcelData;
import com.xdesign.model.ExcelDataVO;
import com.xdesign.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.mockito.Mockito.when;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ExcelApiMappingTest {

    @Mock
    ExcelApiMapping excelApiMapping;

    @Inject
    @Qualifier("testexcel")
    private List<ExcelData> excelData;

    ExcelDataVO invoice ;
    List<ExcelDataVO> invoiceList= new ArrayList<>() ;

    @Before
    public void initialisedata(){

        invoice =  new ExcelDataVO();
        invoice.setRunningNo("988978");
        invoice.setHeightM(new Double(300));
        invoiceList.add(invoice);


        invoice =  new ExcelDataVO();
        invoice.setRunningNo("876786");
        invoice.setHeightM(new Double(200));
        invoiceList.add(invoice);

    }

    @Test
    public void testExcelData() throws InvalidQueryException {
         Optional<String> optionalString = Optional.of("MUN");
        when(excelApiMapping.getExcelData(optionalString)).thenReturn(invoiceList);

       List<ExcelDataVO> invoiceList = excelApiMapping.getExcelData(optionalString);

       assert invoiceList.size() == 2;

    }

    @Test
    public void testExcelDataByCount() throws InvalidQueryException {

        Optional<Integer> optionalInteger = Optional.of(1);
        when(excelApiMapping.getExcelDataByCount(optionalInteger)).thenReturn(invoiceList);

        List<ExcelDataVO> invoiceList = excelApiMapping.getExcelDataByCount(optionalInteger);

        assert invoiceList.size() == 2;
        assert invoiceList.get(0).getHeightM() == 300;

    }


    @Test
    public void testSortByHeightName() throws InvalidQueryException {

        Optional<Double> optionalDouble =  Optional.of(new Double(1300));
        Optional<Double> optionalDouble1 = Optional.of(new Double(500));
        Optional<Util.Order> orderAsc = Optional.of(Util.Order.ASC);

        when(excelApiMapping.getSortByHeightName(orderAsc, orderAsc,   optionalDouble,   optionalDouble1)).thenReturn(invoiceList);

        List<ExcelDataVO> invoiceList = excelApiMapping.getSortByHeightName(orderAsc, orderAsc,   optionalDouble,   optionalDouble1);

        assert invoiceList.size() == 2;

    }
}
