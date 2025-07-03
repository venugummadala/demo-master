package uk.co.fleabay.demo.analytics.exceptions;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileReaderException extends IOException {
    public FileReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
