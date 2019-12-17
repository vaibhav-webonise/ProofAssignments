import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.List;
import exception.DataNotAvailableException;
import exception.InvalidFileNameException;
import service.impl.DataExtractionServiceImpl;

public class DataExtractionServiceImplTest {

  @Test(expected = DataNotAvailableException.class)
  public void testExtractDataFromSheet() throws IOException, DataNotAvailableException, InvalidFileNameException {
    DataExtractionServiceImpl dataExtractionServiceImpl = new DataExtractionServiceImpl();
    dataExtractionServiceImpl.extractDataFromSheet("/home/webonise/demo.xlsx");
  }

  @Test
  public void testExtractDateSeries() throws IOException, InvalidFileNameException {
    DataExtractionServiceImpl dataExtractionServiceImpl = new DataExtractionServiceImpl();
    XSSFSheet sheet = dataExtractionServiceImpl.getSheetFromGivenPath("/home/webonise/Downloads/1.xlsx");
    List<String> testList = dataExtractionServiceImpl.extractDateSeries(sheet);
    Assert.assertEquals("Jan-2016", testList.get(0));
  }

  @Test(expected = InvalidFileNameException.class)
  public void testGetSheetFromGivenPath() throws IOException, InvalidFileNameException {
    DataExtractionServiceImpl dataExtractionServiceImpl = new DataExtractionServiceImpl();
    dataExtractionServiceImpl.getSheetFromGivenPath("/home/webonise/demo2.xlsx");
  }
}
