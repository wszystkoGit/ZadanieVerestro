package pl.wojciechowski.jaroslaw.zadanieverestro.configuration.errorhandling;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.BusinessException;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.errorhandling.dto.ErrorDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.errorhandling.mapper.ErrorMapper;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorMapper errorMapper;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return errorMapper.mapToErrorDto(methodArgumentNotValidException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    //zamiast handlera dla każdego wyjątku, można by je pogrupować w hierarchii i obsłużyć bardziej generycznie
    public ErrorDto handleUserAlreadyExistsException(BusinessException userAlreadyExistsException) {
        return errorMapper.mapToErrorDto(userAlreadyExistsException);
    }
}
