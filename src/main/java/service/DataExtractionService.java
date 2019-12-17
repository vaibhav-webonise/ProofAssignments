package service;

import model.TimeSeries;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import exception.DataNotAvailableException;
import exception.InvalidFileNameException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataExtractionService {
  /*
  This service method will extract the data from xlsx sheet and map to the list of objects
  @param String Filepath
  @return map of string as key and list of object as value
   */
  Map<String, List<TimeSeries>> extractDataFromSheet(String filePath) throws IOException, DataNotAvailableException, InvalidFileNameException;

  /*
  This service method will save data to the appropriate data structure
  @param String, list of Strings and list of Double values
   */
  void saveDataToMap(String cellName, List<String> dateSeries, List<Double> valueSeries);

  /*
  This service method will extract only date series from the xlsx sheet
  @param XSSFSheet object
  @return list of String
   */
  List<String> extractDateSeries(XSSFSheet sheet);

  /*
  This service method will make the object of XSSFSheet from given file path
  @Param path of file in String format
  @return XSSFSheet object
   */
  XSSFSheet getSheetFromGivenPath(String filePath) throws InvalidFileNameException, IOException;
}
