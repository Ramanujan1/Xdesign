package com.xdesign.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import com.poiji.annotation.ExcelCell;

@Entity
public class ExcelData {

    @Id
    @GeneratedValue
    private Integer id;

    @ExcelCell(0)
    private String runningNo;

    @ExcelCell(1)
    private String doBIHNumber;

    @ExcelCell(2)
    private String streetmap;

    @ExcelCell(3)
    private String geograph;

    @ExcelCell(4)
    private String hillBagging;

    @JsonView(Views.Create.class)
    @ExcelCell(5)
    private String name;

    @ExcelCell(6)
    private String smcSection;

    @ExcelCell(7)
    private String rhbSection;


    @ExcelCell(8)
    private String section;

    @JsonView(Views.Create.class)
    @ExcelCell(9)
    private Double heightM;

    @ExcelCell(10)
    private Double heightF;

    @ExcelCell(11)
    private Double map50;

    @ExcelCell(12)
    private Double map25;

    @JsonView(Views.Create.class)
    @ExcelCell(13)
    private String GridRef;

    @ExcelCell(14)
    private String GridRefXY;

    @ExcelCell(15)
    private String xcoord;

    @ExcelCell(16)
    private String ycoord;

    @JsonView(Views.Create.class)
    @ExcelCell(27)
    private String post1997;

    public String getPost1997() {
        return post1997;
    }

    public void setPost1997(String post1997) {
        this.post1997 = post1997;
    }

    public String getRunningNo() {
        return runningNo;
    }

    public void setRunningNo(String runningNo) {
        this.runningNo = runningNo;
    }

    public String getDoBIHNumber() {
        return doBIHNumber;
    }

    public void setDoBIHNumber(String doBIHNumber) {
        this.doBIHNumber = doBIHNumber;
    }

    public String getStreetmap() {
        return streetmap;
    }

    public void setStreetmap(String streetmap) {
        this.streetmap = streetmap;
    }

    public String getGeograph() {
        return geograph;
    }

    public void setGeograph(String geograph) {
        this.geograph = geograph;
    }

    public String getHillBagging() {
        return hillBagging;
    }

    public void setHillBagging(String hillBagging) {
        this.hillBagging = hillBagging;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmcSection() {
        return smcSection;
    }

    public void setSmcSection(String smcSection) {
        this.smcSection = smcSection;
    }

    public String getRhbSection() {
        return rhbSection;
    }

    public void setRhbSection(String rhbSection) {
        this.rhbSection = rhbSection;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Double getHeightM() {
        return heightM;
    }

    public void setHeightM(Double heightM) {
        this.heightM = heightM;
    }

    public Double getHeightF() {
        return heightF;
    }

    public void setHeightF(Double heightF) {
        this.heightF = heightF;
    }

    public Double getMap50() {
        return map50;
    }

    public void setMap50(Double map50) {
        this.map50 = map50;
    }

    public Double getMap25() {
        return map25;
    }

    public void setMap25(Double map25) {
        this.map25 = map25;
    }

    public String getGridRef() {
        return GridRef;
    }

    public void setGridRef(String gridRef) {
        GridRef = gridRef;
    }

    public String getGridRefXY() {
        return GridRefXY;
    }

    public void setGridRefXY(String gridRefXY) {
        GridRefXY = gridRefXY;
    }

    public String getXcoord() {
        return xcoord;
    }

    public void setXcoord(String xcoord) {
        this.xcoord = xcoord;
    }

    public String getYcoord() {
        return ycoord;
    }

    public void setYcoord(String ycoord) {
        this.ycoord = ycoord;
    }

}
