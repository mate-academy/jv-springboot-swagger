package mate.academy.springboot.swagger.model.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotBlank
    private String title;
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;
}
