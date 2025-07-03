package uk.co.fleabay.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.net.MalformedURLException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidURLException extends MalformedURLException {
  public InvalidURLException(String message) {
    super(message);
  }
}
