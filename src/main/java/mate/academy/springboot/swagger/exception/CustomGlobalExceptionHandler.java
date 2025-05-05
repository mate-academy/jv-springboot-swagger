package mate.academy.springboot.swagger.exception;

import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomGlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public String exceptionHandler(NoSuchElementException e) {
        return e.getMessage();
    }
}
