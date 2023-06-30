package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotBlank
    @Size(max = 120)
    private String title;
    @NotNull
    @Min(0)
    private BigDecimal price;
}
