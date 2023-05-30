package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    @Size(min = 4)
    private String title;
    @Positive
    private BigDecimal price;
}
