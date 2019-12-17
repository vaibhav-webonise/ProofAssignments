package service.impl;

import model.TimeSeries;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import exception.DataNotAvailableException;
import exception.InvalidFileNameException;
import service.DataExtractionService;

public class DataExtractionServiceImpl implements DataExtractionService {
  private Map<String, List<TimeSeries>> timeSeriesMap = new HashMap<>();
  private final int SERIES_NO_COLUMN_INDEX = 0;
  private final int SERIES_NAME_COLUMN_INDEX = 1;

  @Override
  public Map<String, List<TimeSeries>> extractDataFromSheet(String filePath) throws IOException, DataNotAvailableException, InvalidFileNameException {
    final int CAMPAIGN_CELL_NO = 1;
    final int CAMPAIGN_DATA_ROW_NO = 2;
    try {
      XSSFSheet sheet = getSheetFromGivenPath(filePath);
      String cellName = "";
      for (Row row : sheet) {
        List<Double> valueSeries = new ArrayList<>();
        for (Cell cell : row) {
          if (row.getRowNum() >= CAMPAIGN_DATA_ROW_NO && row.getRowNum() <= sheet.getLastRowNum() && cell.getColumnIndex() != SERIES_NO_COLUMN_INDEX && cell.getColumnIndex() != SERIES_NAME_COLUMN_INDEX) {
            cellName = row.getCell(CAMPAIGN_CELL_NO).toString();
            valueSeries.add(cell.getNumericCellValue());
          }
        }
        saveDataToMap(cellName, extractDateSeries(sheet), valueSeries);
      }
      if (timeSeriesMap.isEmpty()) {
        throw new DataNotAvailableException("Data not available");
      } else {
        return timeSeriesMap;
      }
    } catch (InvalidFileNameException e) {
      throw new InvalidFileNameException("You might have entered wrong file name");
    }
  }

  @Override
  public void saveDataToMap(String cellName, List<String> dateSeries, List<Double> valueSeries) {
    if (!cellName.isEmpty() && !dateSeries.isEmpty() && !valueSeries.isEmpty()) {
      List<TimeSeries> timeSeriesList = new ArrayList<>();
      for (int i = 0; i < dateSeries.size(); i++) {
        timeSeriesList.add(new TimeSeries(dateSeries.get(i), valueSeries.get(i)));
      }
      timeSeriesMap.put(cellName, timeSeriesList);
    }
  }

  @Override
  public List<String> extractDateSeries(XSSFSheet sheet) {
    final int ROW_ONE = 1;
    List<String> dateSeries = new ArrayList<>();
    for (Row row : sheet) {
      for (Cell cell : row) {
        if (row.getRowNum() == ROW_ONE) {
          if (cell.getColumnIndex() != SERIES_NO_COLUMN_INDEX && cell.getColumnIndex() != SERIES_NAME_COLUMN_INDEX) {
            dateSeries.add(cell.getStringCellValue());
          }
        }
      }
    }
    return dateSeries;
  }

  @Override
  public XSSFSheet getSheetFromGivenPath(String filePath) throws InvalidFileNameException, IOException {
    try {
      FileInputStream fileInputStream = new FileInputStream(new File(filePath));
      XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
      return xssfWorkbook.getSheetAt(0);
    } catch (FileNotFoundException e) {
      throw new InvalidFileNameException(e.getMessage());
    }
  }
}
