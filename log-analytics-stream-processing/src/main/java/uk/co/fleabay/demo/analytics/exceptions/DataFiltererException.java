package uk.co.fleabay.demo.analytics.exceptions;

public class DataFiltererException extends RuntimeException {

    public DataFiltererException(Exception e) {
        super(e);
    }
}
