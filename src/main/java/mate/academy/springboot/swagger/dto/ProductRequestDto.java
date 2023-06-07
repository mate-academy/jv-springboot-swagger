package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotBlank
    @Size(min = 1, max = 120)
    private String title;
    @NotBlank
    @Size(min = 0)
    private BigDecimal price;
}
