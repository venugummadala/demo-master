package uk.co.fleabay.demo.analytics;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DataFilterer {

    private static final Logger logger = LogManager.getLogger(DataFilterer.class);


  public static List<String> filterByCountry(Reader source, String country) {

    BufferedReader bufferedReader = (BufferedReader) source;
    List<String> filteredSortedLines = new ArrayList<>();

    Stream< String > lines       = bufferedReader.lines();
    filteredSortedLines  =
            lines
                    .skip( 1L )  // Skip the header row, with column names.
                    .filter(  // Filter out lines whose targeted value is equal to param "country".
                            line ->  line.split( "," )[ 1 ].equalsIgnoreCase( country )
                    )
                    .sorted(  // Extract third field’s text and sort (sort on 'RESPONSE_TIME')
                            Comparator.comparingInt( (String line ) -> Integer.parseInt( line.split( "," )[ 2 ] ) )
                    )

                    .collect(Collectors.toList() );// Collect into a List


    filteredSortedLines.forEach(logger::info); // 'Clean-up' or log as INFO


    return filteredSortedLines;
  }


  public static List<String> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {

      BufferedReader bufferedReader = (BufferedReader) source;
      List<String> filteredSortedLines = new ArrayList<>();

      Stream< String > lines       = bufferedReader.lines();
      filteredSortedLines  =
              lines
                      .skip( 1L )  // Skip the header row, with column names.
                      .filter(line -> line.split(",")[1].equalsIgnoreCase(country)) // Split each line by comma & match param 'country'

                      .filter(  // Filter out lines whose RESPONSE_TIME is greater than (>) param 'limit'.
                              line -> { //Long.parseLong( line.split(",")[ 2 ]) > limit
                                  String[] parts = line.split(",");
                                  return parts[1].equalsIgnoreCase(country) &&
                                          (limit < 0 || Long.parseLong(parts[2]) > limit);
                              }
                      )

                      .sorted(  // Extract third field’s text and sort (sort on 'RESPONSE_TIME')
                              Comparator.comparingInt( (String line ) -> Integer.parseInt( line.split( "," )[ 2 ] ) )
                      )
                      .collect(Collectors.toList()); // Collect into a List

      filteredSortedLines.forEach(logger::info); // 'Clean-up' or log as INFO

      return filteredSortedLines;
  }




}


