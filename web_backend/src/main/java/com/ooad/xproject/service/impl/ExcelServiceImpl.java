package com.ooad.xproject.service.impl;

import com.ooad.xproject.bo.*;
import com.ooad.xproject.config.FileConfig;
import com.ooad.xproject.service.EATaskService;
import com.ooad.xproject.service.ExcelService;
import com.ooad.xproject.service.ProjectService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelServiceImpl implements ExcelService {

    private final FileConfig fileConfig;

    private final ProjectService projectService;

    private final EATaskService eaTaskService;

    public ExcelServiceImpl(FileConfig fileConfig, ProjectService projectService, EATaskService eaTaskService) {
        this.fileConfig = fileConfig;
        this.projectService = projectService;
        this.eaTaskService = eaTaskService;
    }

    //    objectList: List of data
//    filePath: output path of excel
//    fields: Output list of fields of the class of data
//    titles: first row
    @Override
    public SvResult<String> generate(List<?> objectList, String filePath, String[] fields, String[] titles) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        int rowCount = 0;

        Row row = sheet.createRow(rowCount++);

        for (int i = 0; i < titles.length; ++i) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titles[i]);
        }

        for (Object o : objectList) {
            row = sheet.createRow(rowCount++);
            for (int j = 0; j < fields.length; ++j) {
                Cell cell = row.createCell(j);
                String str;
                try {
                    str = Objects.requireNonNull(getFieldValueByName(fields[j], o)).toString();
                } catch (Exception e) {
                    str = "";
                }
                cell.setCellValue(str);
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            rowCount = 0;
        }

        String msg = (rowCount == 0) ? "null path" : filePath;
        return new SvResult<String>(rowCount, "", msg);
    }

    //  titles = fields
    @Override
    public SvResult<String> generate(List<?> objectList, String filePath, String[] fields) {
        return generate(objectList, filePath, fields, fields);
    }

    //    fields = all the fields in class of data
//    titles = fields
    @Override
    public SvResult<String> generate(List<?> objectList, String filePath) {
        String[] fields;
        fields = new String[0];

        if (!objectList.isEmpty())
            fields = getField(objectList.get(0).getClass());

        return generate(objectList, filePath, fields, fields);
    }

    @Override
    public List<StudentImportBO> readStudentImportBO(String filePath) {
        Workbook workbook = readWorkbook(filePath);
        Sheet sheet = workbook.getSheetAt(0);

        List<StudentImportBO> ret = new ArrayList<>();

        if (sheet != null) {
            Row titleRow = sheet.getRow(sheet.getFirstRowNum());
            List<String> titleList = new ArrayList<>(titleRow.getLastCellNum() - titleRow.getFirstCellNum());
            for (int j = titleRow.getFirstCellNum(); j < titleRow.getLastCellNum(); ++j) {
                titleList.add(readCellValueToString(titleRow.getCell(j)));
            }

            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); ++i) {
                Row row = sheet.getRow(i);
                StudentImportBO studentImportBO = new StudentImportBO();
                List<String> rowList = new ArrayList<>(titleRow.getLastCellNum() - titleRow.getFirstCellNum());
                for (int j = titleRow.getFirstCellNum(); j < titleRow.getLastCellNum(); ++j) {
                    rowList.add(readCellValueToString(row.getCell(j)));
                }

                for (int j = 0; j < titleList.size(); ++j) {
                    if ("username".equals(titleList.get(j))) {
                        studentImportBO.setUsername(rowList.get(j));
                    } else if ("password".equals(titleList.get(j))) {
                        studentImportBO.setPassword(rowList.get(j));
                    } else if ("stdName".equals(titleList.get(j))) {
                        studentImportBO.setStdName(rowList.get(j));
                    } else if ("stdNo".equals(titleList.get(j))) {
                        studentImportBO.setStdNo(rowList.get(j));
                    } else if ("stdClass".equals(titleList.get(j))) {
                        studentImportBO.setStdClass(rowList.get(j));
                    } else if ("email".equals(titleList.get(j))) {
                        studentImportBO.setEmail(rowList.get(j));
                    }
                }
                ret.add(studentImportBO);
            }

        }
//        System.out.println(sheet.getFirstRowNum());
        return ret;
    }

    @Override
    public List<RecordUnitBO> readRecordUnitBO(String filePath) {
        Workbook workbook = readWorkbook(filePath);
        Sheet sheet = workbook.getSheetAt(0);

        List<RecordUnitBO> ret = new ArrayList<>();

        if (sheet != null) {
            Row titleRow = sheet.getRow(sheet.getFirstRowNum());
            List<String> titleList = new ArrayList<>(titleRow.getLastCellNum() - titleRow.getFirstCellNum());
            for (int j = titleRow.getFirstCellNum(); j < titleRow.getLastCellNum(); ++j) {
                titleList.add(readCellValueToString(titleRow.getCell(j)));
            }

            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); ++i) {
                Row row = sheet.getRow(i);
                RecordUnitBO recordUnitBO = new RecordUnitBO();
                List<String> rowList = new ArrayList<>(titleRow.getLastCellNum() - titleRow.getFirstCellNum());
                for (int j = titleRow.getFirstCellNum(); j < titleRow.getLastCellNum(); ++j) {
                    rowList.add(readCellValueToString(row.getCell(j)));
                }

                for (int j = 0; j < titleList.size(); ++j) {
                    if ("rcdName".equals(titleList.get(j))) {
                        recordUnitBO.setRcdName(rowList.get(j));
                    } else if ("stdName".equals(titleList.get(j))) {
                        recordUnitBO.setStdName(rowList.get(j));
                    } else if ("stdNo".equals(titleList.get(j))) {
                        recordUnitBO.setStdNo(rowList.get(j));
                    } else if ("grade".equals(titleList.get(j))) {
                        recordUnitBO.setGrade(rowList.get(j));
                    } else if ("type".equals(titleList.get(j))) {
                        recordUnitBO.setType(rowList.get(j));
                    } else if ("comments".equals(titleList.get(j))) {
                        recordUnitBO.setComments(rowList.get(j));
                    }
                }
                ret.add(recordUnitBO);
            }

        }
//        System.out.println(sheet.getFirstRowNum());
        return ret;
    }

    @Override
    public List<StudentClassBO> readStudentClassBO(String filePath) {
        Workbook workbook = readWorkbook(filePath);
        Sheet sheet = workbook.getSheetAt(0);

        List<StudentClassBO> ret = new ArrayList<>();

        if (sheet != null) {
            Row titleRow = sheet.getRow(sheet.getFirstRowNum());
            List<String> titleList = new ArrayList<>(titleRow.getLastCellNum() - titleRow.getFirstCellNum());
            for (int j = titleRow.getFirstCellNum(); j < titleRow.getLastCellNum(); ++j) {
                titleList.add(readCellValueToString(titleRow.getCell(j)));
            }

            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); ++i) {
                Row row = sheet.getRow(i);
                StudentClassBO studentClassBO = new StudentClassBO();
                List<String> rowList = new ArrayList<>(titleRow.getLastCellNum() - titleRow.getFirstCellNum());
                for (int j = titleRow.getFirstCellNum(); j < titleRow.getLastCellNum(); ++j) {
                    rowList.add(readCellValueToString(row.getCell(j)));
                }

                for (int j = 0; j < titleList.size(); ++j) {
                    if ("stdNo".equals(titleList.get(j))) {
                        studentClassBO.setStdNo(rowList.get(j));
                    } else if ("clsMark".equals(titleList.get(j))) {
                        studentClassBO.setClsMark(rowList.get(j));
                    }
                }
                ret.add(studentClassBO);
            }
        }
        return ret;
    }

    @Override
    public SvResult<String> exportRecordUnitByProjId(Integer projId) {
        String filePath = fileConfig.getOutputRoot() + "\\" + "output.xlsx";
        List<RecordUnitBO> recordUnitList = projectService.getRecordUnitList(projId);
        return generate(recordUnitList, filePath);
    }

    @Override
    public SvResult<String> exportTeamByProjId(Integer projId) {
        String filePath = fileConfig.getOutputRoot() + "\\" + "output.xlsx";
        List<TeamBO> recordUnitList = projectService.getTeamList(projId);
        return generate(recordUnitList, filePath);
    }

    @Override
    public SvResult<String> exportEventInst(Integer eaId) {
        String filePath = fileConfig.getOutputRoot() + "\\" + "output.xlsx";
        List<EventInstBO> eventInstBOS = eaTaskService.getEventInstBOList(eaId);
        return generate(eventInstBOS, filePath);
    }

    Workbook readWorkbook(String filePath) {
        Workbook workbook = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public String[] getField(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
//            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {

            return null;
        }
    }

    String readCellValueToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType type = cell.getCellType();
        switch (type) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case NUMERIC:
                DecimalFormat df = new DecimalFormat("0");
                return String.valueOf(df.format(cell.getNumericCellValue()));
            case FORMULA:
                return String.valueOf(cell.getCellFormula());
            default:
                return cell.toString();
        }
    }
}
