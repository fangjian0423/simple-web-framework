package org.format.framework.util;

import org.apache.poi.hssf.usermodel.*;
import org.format.framework.annotation.ExcelAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil<T> {

    final public static String TIME_FORMAT = "^\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?$";
    private Class<T> clazz;

    public ExcelUtil(Class<T> clazz){
        this.clazz = clazz;
    }

    public String getSheetName(){
        return clazz.getAnnotation(ExcelAnnotation.class).sheetName();
    }

    public List<ExcelField> getFields() throws SecurityException, NoSuchMethodException {
        List<ExcelField> list = new ArrayList<ExcelField>();
        Field filed[] = clazz.getDeclaredFields();
        for (int i = 0; i < filed.length; i++) {
            Field field = filed[i];
            ExcelAnnotation exa = field.getAnnotation(ExcelAnnotation.class);
            if (exa != null){
                ExcelField myField = new ExcelField(clazz,field,exa);
                list.add(myField);
            }
            Comparator<ExcelField> myComparator = new MyComparator();
            Collections.sort(list, myComparator);
        }
        return list;
    }

    public int getHeaderHeight() {
        return 20;
    }
    public int geBodyHeight() {
        return 16;
    }
    // 表头样式
    public HSSFCellStyle setHeaderStyle(HSSFWorkbook workbook) {
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short)13);
        headerStyle.setFont(font);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerStyle.setFillBackgroundColor((short)0xfcfcfc);
        return headerStyle;
    }
    // 单元格样式
    public HSSFCellStyle setCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        return cellStyle;
    }
    public HSSFWorkbook exportExcel(List<T> lst) throws Exception {
        return exportExcel(null,null,lst);
    }
    public HSSFWorkbook exportExcel(String sheetName,List<T> lst) throws Exception {
        return exportExcel(null,sheetName,lst);
    }
    public HSSFWorkbook exportExcel(HSSFWorkbook workbook,String sheetName,List<T> lst) throws Exception {
        if(workbook==null){
            workbook = new HSSFWorkbook();
        }
        HSSFCellStyle headerStyle = setHeaderStyle(workbook);
        HSSFCellStyle cellStyle = setCellStyle(workbook);

        HSSFSheet sheet = null;
        HSSFRow row = null;
        List<ExcelField> fields = getFields();
        sheet = workbook.createSheet(sheetName==null?getSheetName():sheetName);
        row = sheet.createRow(0);
        row.setHeightInPoints(getHeaderHeight());
        for (int i = 0; i < fields.size(); i++) {
            ExcelField field = fields.get(i);
            HSSFCell cell = row.createCell(i);
            sheet.setColumnWidth(i,field.getWidth());
            cell.setCellValue(field.getTitleName());
            cell.setCellStyle(headerStyle);
        }

        for(int rowindex=0;rowindex<lst.size();rowindex++){
            T model = (T) lst.get(rowindex);
            row = sheet.createRow(rowindex+1);
            row.setHeightInPoints(geBodyHeight());
            for (int column = 0; column < fields.size(); column++) {
                ExcelField field = fields.get(column);
                Object value = field.getMethod().invoke(model);
                HSSFCell cell = row.createCell(column);
                if (value == null){
                    cell.setCellValue("");
                } else if (field.isInt()){
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(String.valueOf(value));
                } else if (value instanceof Date) {
                    String insertVal = getDateTime((Date) value, "yyyy-MM-dd HH:mm");
                    cell.setCellValue(insertVal);
                } else {
                    String insertVal = "";
                    if (value.toString().matches(TIME_FORMAT)) {
                        insertVal = value.toString().substring(0,16);
                    } else {
                        insertVal = value.toString();
                    }
                    cell.setCellValue(insertVal);
                }
                cellStyle.setAlignment(field.getAlign());
                cell.setCellStyle(cellStyle);
            }
        }
        return workbook;
    }

    public String getDateTime(Date value,String format) {
        if (value != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(value);
        }
        return "";
    }
    public String changeBoolean(String str) {
        if("1".equals(str))return "是";
        else if("0".equals(str)) return "否";
        else return "未知";
    }
}

class ExcelField {
    private Class<?> clazz;
    private Field field;
    private ExcelAnnotation exa;
    private int sort;
    private short align;
    public ExcelField(Class<?> clazz,Field field,ExcelAnnotation exa) {
        this.clazz = clazz;
        this.field = field;
        this.exa = exa;
        this.sort = exa.sort();
        this.align = exa.align();
    }
    public String getTitleName(){
        return exa.titleName();
    }
    public Method setMethod() throws SecurityException, NoSuchMethodException{
        String method = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        return clazz.getMethod(method,field.getType());
    }
    public Method getMethod() throws SecurityException, NoSuchMethodException{
        String method = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        return clazz.getMethod(method);
    }
    public int getSort() {
        return sort;
    }
    public short getAlign() {
        return align;
    }
    public int getWidth(){
        return exa.width()*150;
    }
    public boolean isInt(){
        return exa.isInt();
    }
}

class MyComparator implements Comparator<ExcelField> {
    public MyComparator() {
    }
    @Override
    public int compare(ExcelField o1, ExcelField o2) {
        return o1.getSort()>o2.getSort()?1:0;
    }
}
