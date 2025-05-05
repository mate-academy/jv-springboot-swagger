package mate.academy.springboot.swagger.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestDto {
    @NotNull
    private String title;
    @Positive
    private Long price;
}
