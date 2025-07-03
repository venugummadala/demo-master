package uk.co.fleabay.demo.analytics;


import java.io.BufferedReader;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import uk.co.fleabay.demo.analytics.util.DataReaderUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class DataFiltererTest {
    private static final String FILE_NAME = "multi-lines";
    private static final int RESPONSE_TIME_COLUMN_INDEX = 2;
    private static final int EXPECTED_AVERAGE_RESPONSE_TIME = 343;
    private static final int RESPONSE_TIME_LIMIT = 800;
    private static final Logger logger = LogManager.getLogger(DataFiltererTest.class);

    static {
        System.setProperty("log4j2.debug", "true");
    }

  @Test
  void shouldReturnAverageResponseTime_WhenLogFileIsNotEmpty() {

      try {
      Stream<String> logFileData = DataReaderUtil.getResourceAsStream(FILE_NAME);
      assertNotNull(logFileData);


      //Read only Once from the physical file
      final String[] masterStream = logFileData.toArray(String[]::new);

      Supplier<Stream<String>> newStreamSupplier3 = () -> Stream.of(masterStream);

      Stream<String>      stream4AvgResponseLimit =  newStreamSupplier3.get();
      long averageResponseTimeLimit      =  DataReaderUtil.getAverageResponseTime(stream4AvgResponseLimit, RESPONSE_TIME_COLUMN_INDEX);
      //assertNotNull(averageResponseTimeLimit);
      assertEquals(EXPECTED_AVERAGE_RESPONSE_TIME, averageResponseTimeLimit);

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
  }


    @Test
    void shouldReturnNLCountriesOnly_WhenLogFileIsNotEmpty()  {
       // final String FILE_NAME = "multi-lines";
        final String COUNTRY_NL = "NL";
        try {
        BufferedReader bufferedReader4FilterByCountry = DataReaderUtil.getResourceAsReader(FILE_NAME);

      //  List<String> result =  DataFilterer.filterByCountry(bufferedReader4FilterByCountry, COUNTRY_NL);
            List<String> result =  DataFilterer.filterByCountryWithResponseTimeAboveLimit(bufferedReader4FilterByCountry, COUNTRY_NL, -1);
            logger.info(result.get(0));
        assertNotNull(result);
       assertEquals("1432917066,NL,0", result.get(0));
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void shouldReturnResponseTimeAboveLimit_WhenLogFileIsNotEmpty()  {
        //final String FILE_NAME = "multi-lines";
        final String COUNTRY_US = "US";
        try {
        BufferedReader bufferedReaderUS = DataReaderUtil.getResourceAsReader(FILE_NAME);
        List<String> result =  DataFilterer.filterByCountryWithResponseTimeAboveLimit(bufferedReaderUS, COUNTRY_US, RESPONSE_TIME_LIMIT);
        logger.info(result.get(0));
        assertNotNull(result);
        assertEquals("1432484176,US,850", result.get(0));
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }





}
