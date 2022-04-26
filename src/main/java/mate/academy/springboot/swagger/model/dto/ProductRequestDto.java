package mate.academy.springboot.swagger.model.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    @NotEmpty(message = "Enter product name, 3 or more symbols")
    @Size(min = 3)
    private String title;
    @NotEmpty (message = "Enter price bigger than 1")
    @Min(value = 1)
    private BigDecimal price;
}
