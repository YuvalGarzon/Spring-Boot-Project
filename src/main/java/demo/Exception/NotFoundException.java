package demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
  private static final long serialVersionUID = 3119877120968775776L;
  public NotFoundException() {
  }
  public NotFoundException(String message) {
    super(message);
  }
  
  public NotFoundException(Throwable cause) {
    super(cause);
  }
  
  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
  
}
