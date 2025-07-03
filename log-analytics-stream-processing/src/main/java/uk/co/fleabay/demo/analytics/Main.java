package uk.co.fleabay.demo.analytics;

import uk.co.fleabay.demo.analytics.util.DataReaderUtil;

import java.io.BufferedReader;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

static final String FILE_NAME = "multi-lines";
    static final String COUNTRY_GB = "GB";
    static final String COUNTRY_US = "US";

    public static void main(String args[]) throws Exception{


      Stream<String> logFileData =  DataReaderUtil.getResourceAsStream(FILE_NAME);


        //Read only Once from the physical file
        final String[] masterStream = logFileData.toArray(String[]::new);

        Supplier<Stream<String>> newStreamSupplier1 = () -> Stream.of(masterStream);
        Supplier<Stream<String>> newStreamSupplier2 = () -> Stream.of(masterStream);

        Supplier<Stream<String>> newStreamSupplier3 = () -> Stream.of(masterStream);


        Stream<String>      logFileDataStream       = newStreamSupplier1.get();
        Stream<String>      logFileDataStream4Header = newStreamSupplier2.get();
        Stream<String>      stream4AvgResponseLimit =  newStreamSupplier3.get();
        Optional<String>    headerLine              = logFileDataStream4Header.findFirst();

        // Check if the header line is present and print it
        headerLine.ifPresent(line -> logger.info("Header: HHHHHHH " + line));

        long averageResponseTimeLimit      =  DataReaderUtil.getAverageResponseTime(stream4AvgResponseLimit, 2);

        BufferedReader bufferedReaderGB = DataReaderUtil.getResourceAsReader(FILE_NAME);
       DataFilterer.filterByCountryWithResponseTimeAboveLimit(bufferedReaderGB, COUNTRY_GB, 0);

        BufferedReader bufferedReaderUS = DataReaderUtil.getResourceAsReader(FILE_NAME);
        DataFilterer.filterByCountryWithResponseTimeAboveLimit(bufferedReaderUS, COUNTRY_US, 600);
        DataFilterer.filterByCountryWithResponseTimeAboveLimit(bufferedReaderUS, COUNTRY_US, 600);

        BufferedReader bufferedReader4FilterByCountry = DataReaderUtil.getResourceAsReader(FILE_NAME);

        DataFilterer.filterByCountry(bufferedReader4FilterByCountry, COUNTRY_US);





    }

}