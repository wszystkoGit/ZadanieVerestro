package pl.wojciechowski.jaroslaw.zadanieverestro.logic.errorhandling.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorDto {
    private String message;
    private List<String> details;
}
