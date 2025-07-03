package uk.co.fleabay.demo.analytics.util;

import uk.co.fleabay.demo.analytics.exceptions.FileReaderException;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DataReaderUtil {
    private static final Logger logger = LogManager.getLogger(DataReaderUtil.class);

    public static Stream<String> getResourceAsStream(String filename) throws FileReaderException, Exception {
        try {
            return Files.lines(Paths.get(ClassLoader.getSystemResource(filename).toURI()));
        } catch (URISyntaxException e) {
            logger.error("Error with URI Syntax for file: " + filename, e);
            throw new URISyntaxException("Incorrect URI Syntax", e.getMessage());
        } catch (IOException e) {
            logger.error("Error reading file: " + filename, e);
            throw new FileReaderException("Error Reading File: " + filename, e);
        }

    }

    public static BufferedReader getResourceAsReader(String filename) throws FileReaderException, Exception {
        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(filename);
            if (inputStream == null) {
                throw new FileReaderException("File not found: " + filename, null);
            }
            return new BufferedReader(new InputStreamReader(inputStream));
        } catch (Exception e) {
            logger.error("Error reading file: " + filename, e);
            throw new FileReaderException("Error Reading File: " + filename, e);
        }
    }




    public static  Optional<String> getHeaderRow(byte[] streamLogFileData) {
        // Please note: this is a terminal operation
        BufferedReader bufferedReader = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(streamLogFileData);
            bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream));
           Optional<String> headerLine = Optional.of(bufferedReader.readLine());
           bufferedReader.close();;
            return headerLine;
        } catch (IOException ioException) {
            logger.error("Error reading header row from byte array: " + ioException.getMessage());
            throw new RuntimeException("Error reading header row from byte array", ioException);
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            throw new RuntimeException("Unexpected error while reading header row", e);
        }

        finally {
           if(bufferedReader != null) {
               try {
                   bufferedReader.close();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }

        }
        //return Optional.empty();
    }


    public static  Optional<String> getHeaderRow(Stream<String> streamLogFileData) {
           // Please note: this is a terminal operation
            Optional<String>  headerLine = streamLogFileData.findFirst();

            return    headerLine;
        }


    public static byte[] readFileIntoByteArray(String filename) {

        try {
            byte[] byteArrFileContent = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(filename).toURI()));

            if(byteArrFileContent.length > 0 ) return byteArrFileContent;



        } catch (URISyntaxException | IOException uriSyntaxException) {
            logger.info("byte stream" + uriSyntaxException );

        }

        return new byte[0];
    }

    public static Stream<String> getANewStreamFromByteContent(byte[] byteArrFileContent) {

       // Stream byteStream =  Stream.of(new String(byteArrFileContent, StandardCharsets.UTF_8));
        Stream byteStream =  Stream.of(new String(byteArrFileContent));

        return byteStream;
    }

public static long getAverageResponseTime(Stream<String> logFileDataStream, int columnIndexOfRESPONSE_TIME) {


    try {
        // Read the CSV file and calculate the average of the specified column
        OptionalDouble average = logFileDataStream
                .skip(1) // Skip the header line
                .map(line -> line.split(",")) // Split each line by comma
                .filter(columns -> columns.length > 0) // Ensure the column exists
                .mapToDouble(columns -> Long.parseLong(columns[columnIndexOfRESPONSE_TIME])) // Convert to double
                .average();
               // .getAsDouble();
        if (average.isPresent()) {
            logger.info("DataReaderUtil::getAverageResponseTime Average value:  " + (long) average.getAsDouble());
            return (long) average.getAsDouble();
        } else {
            logger.info("No data available to calculate average.");
            return 0;
        }



    } catch (NumberFormatException e) {
        // Handle the case where parsing fails
        logger.error("Error parsing number: " + e.getMessage());
    }

    return 0;
}


}