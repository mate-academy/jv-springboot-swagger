package mate.academy.springboot.swagger.exception;

import mate.academy.springboot.swagger.dto.ExceptionDto;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomGlobalExceptionHandler {

    @ExceptionHandler(DataProcessException.class)
    public ExceptionDto exceptionHandler(DataProcessException e) {
        return new ExceptionDto(e.getMessage());
    }
}
