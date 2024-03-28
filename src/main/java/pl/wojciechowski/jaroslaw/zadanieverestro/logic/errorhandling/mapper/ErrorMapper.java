package pl.wojciechowski.jaroslaw.zadanieverestro.logic.errorhandling.mapper;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.BusinessException;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.errorhandling.dto.ErrorDto;

import java.util.List;

@Component
public class ErrorMapper {
    public ErrorDto mapToErrorDto(MethodArgumentNotValidException methodArgumentNotValidException) {
        String objectName = methodArgumentNotValidException.getObjectName();
        List<String> details = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(this::mapToDetail)
                .toList();
        return ErrorDto.builder()
                .message("Validation failed for object: " + objectName)
                .details(details)
                .build();
    }

    public ErrorDto mapToErrorDto(BusinessException businessException) {
        return ErrorDto.builder()
                .message(businessException.getMessage())
                .build();
    }


    private String mapToDetail(FieldError error) {
        return error.getField() + ":" + error.getDefaultMessage();
    }

}
