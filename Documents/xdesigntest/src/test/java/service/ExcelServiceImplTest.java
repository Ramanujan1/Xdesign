package service;

import com.xdesign.ApplicationMain;
import com.xdesign.model.ExcelData;
import com.xdesign.service.ExcelPoijiService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMain.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ExcelServiceImplTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ExcelPoijiService excelPoijiService;

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();

    @Inject
    @Qualifier("testexcel")
    private List<ExcelData> excelData;

    @Test
    public void testListfromExcelDataCount() {
        assert excelData.size() == 608;
    }

    @Test
    public void testListfromExcelDataCount1() {
        assert excelPoijiService.getListfromExcelData().size() == 608;
    }

    @Test
    public void testMvcStatus() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange("http://localhost:"+port+"/sortByHeightName?heightOrder=ASC&nameOrder=ASC", HttpMethod.GET,entity, List.class);
        assertEquals(responseEntity.getStatusCode().value() , (200));
    }

    @Test
    public void testDataTestAscHeightName() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);
        ResponseEntity<List<ExcelData>> responseEntity = restTemplate.exchange("http://localhost:"+port+"/sortByHeightName?heightOrder=ASC&nameOrder=ASC", HttpMethod.GET,entity, new ParameterizedTypeReference<List<ExcelData>>() {});
        List<ExcelData> invoiceList = responseEntity.getBody();

        assertEquals(invoiceList.get(0).getHeightM().toString() , "0.0");
        assertEquals(invoiceList.get(invoiceList.size() - 1).getHeightM().toString() , "1344.53");

        assertEquals(invoiceList.get(1).getName().toString() , "Sgurr a' Mhaoraich - Am Bathaich");
        assertEquals(invoiceList.get(invoiceList.size() - 1).getName().toString() , "Ben Nevis");

    }

    @Test
    public void testDataTestDscHeight() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);
        ResponseEntity<List<ExcelData>> responseEntity = restTemplate.exchange("http://localhost:"+port+"/sortByHeightName?heightOrder=DSC&nameOrder=ASC", HttpMethod.GET,entity, new ParameterizedTypeReference<List<ExcelData>>() {});
        List<ExcelData> invoiceList = responseEntity.getBody();

        assertEquals(invoiceList.get(0).getHeightM().toString() , "1344.53");
        assertEquals(invoiceList.get(invoiceList.size() - 1).getHeightM().toString() , "0.0");

        assertEquals(invoiceList.get(1).getName().toString() , "Ben Macdui [Beinn Macduibh]");
        assertEquals(invoiceList.get(invoiceList.size() - 2).getName().toString() , "Sgurr a' Mhaoraich - Am Bathaich");

    }

    @Test
    public void testDataTestDscHeightOnly() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);
        ResponseEntity<List<ExcelData>> responseEntity = restTemplate.exchange("http://localhost:"+port+"/sortByHeightName?heightOrder=DSC", HttpMethod.GET,entity, new ParameterizedTypeReference<List<ExcelData>>() {});
        List<ExcelData> invoiceList = responseEntity.getBody();

        assertEquals(invoiceList.get(0).getHeightM().toString() , "1344.53");
        assertEquals(invoiceList.get(invoiceList.size() - 1).getHeightM().toString() , "0.0");

        assertEquals(invoiceList.get(1).getName().toString() , "Ben Macdui [Beinn Macduibh]");
        assertEquals(invoiceList.get(invoiceList.size() - 2).getName().toString() , "Sgurr a' Mhaoraich - Am Bathaich");

    }


    @Test
    public void testDataTestAscHeightNameMaxAndMin() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);
        ResponseEntity<List<ExcelData>> responseEntity = restTemplate.exchange("http://localhost:"+port+"/sortByHeightName?heightOrder=ASC&nameOrder=ASC&maxHeigh=1200&minHeigh=950", HttpMethod.GET,entity, new ParameterizedTypeReference<List<ExcelData>>() {});
        List<ExcelData> invoiceList = responseEntity.getBody();

        assertEquals(invoiceList.get(0).getHeightM().toString() , "951.0");
        assertEquals(invoiceList.get(invoiceList.size() - 1).getHeightM().toString() , "1197.0");

        assertEquals(invoiceList.get(1).getName().toString() , "Toll Creagach West Top");
        assertEquals(invoiceList.get(invoiceList.size() - 1).getName().toString() , "Beinn a' Bhuird [Beinn a' Bhuird North Top]");

    }


    @Test
    public void testFilterByHillCategory() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);
        ResponseEntity<List<ExcelData>>  responseEntity = restTemplate.exchange("http://localhost:" + port + "/filterByHillCategory?hillCategory=MUN", HttpMethod.GET, entity, new ParameterizedTypeReference<List<ExcelData>>(){});
        List<ExcelData> invoiceList = responseEntity.getBody();
        if (invoiceList.stream().filter(invoice -> !invoice.getPost1997().equals("MUN")).count() > 0) {
            assert false;
        } else {
            assert true;
        }
    }

    @Test
    public void testDataLimitCount() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);
        ResponseEntity<List<ExcelData>>  responseEntity = restTemplate.exchange("http://localhost:" + port + "/getDataLimitCount?count=10", HttpMethod.GET, entity, new ParameterizedTypeReference<List<ExcelData>>(){});
        List<ExcelData> invoiceList = responseEntity.getBody();
        if (invoiceList.size() > 10 || invoiceList.size() < 10) {
            assert false;
        } else {
            assert true;
        }
    }



    @Test
    public void testDataLimitCountException() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);

        Exception exception = assertThrows(HttpClientErrorException.Conflict.class, () -> {
            ResponseEntity<List<ExcelData>>  responseEntity = restTemplate.exchange("http://localhost:" + port + "/getDataLimitCount?count=-1", HttpMethod.GET, entity, new ParameterizedTypeReference<List<ExcelData>>(){});
        });
    }

    @Test
    public void testHillCategoryException() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);

        Exception exception = assertThrows(HttpClientErrorException.Conflict.class, () -> {
            ResponseEntity<List<ExcelData>>  responseEntity = restTemplate.exchange("http://localhost:" + port + "/filterByHillCategory?hillCategory=MON", HttpMethod.GET, entity, new ParameterizedTypeReference<List<ExcelData>>(){});
        });
    }

    @Test
    public void testSortByHeightException() throws Exception{

        HttpEntity entity = new HttpEntity(null, httpHeaders);

        Exception exception = assertThrows(HttpClientErrorException.Conflict.class, () -> {
            ResponseEntity<List<ExcelData>>  responseEntity = restTemplate.exchange("http://localhost:" + port + "/sortByHeightName?heightOrder=ASC&nameOrder=ASC&maxHeigh=-1", HttpMethod.GET, entity, new ParameterizedTypeReference<List<ExcelData>>(){});
        });
    }
}