import model.TimeSeries;
import exception.DataNotAvailableException;
import exception.InvalidFileNameException;
import service.impl.DataExtractionServiceImpl;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DemoApplication {
  public static void main(String[] args) throws IOException, DataNotAvailableException, InvalidFileNameException {
    DataExtractionServiceImpl dataExtractionServiceImpl = new DataExtractionServiceImpl();
    Map<String, List<TimeSeries>> timeSeriesMap = dataExtractionServiceImpl.extractDataFromSheet("/home/webonise/Downloads/1.xlsx");
    for (Map.Entry<String, List<TimeSeries>> entry : timeSeriesMap.entrySet()) {
      System.out.println("\n Campaign = " + entry.getKey());
      for (int i = 0; i < entry.getValue().size(); i++) {
        System.out.println(" Date: " + entry.getValue().get(i).getDate() + "   Value: " + entry.getValue().get(i).getSpends());
      }
    }
  }
}
