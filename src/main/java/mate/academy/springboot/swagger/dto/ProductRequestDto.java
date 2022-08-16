package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotBlank(message = "mustn't be blank")
    private String title;

    @NotNull(message = "mustn't be null")
    @DecimalMin(value = "0.01")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
}
