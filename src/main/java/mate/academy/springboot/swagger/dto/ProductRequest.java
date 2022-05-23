package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    @Size(min = 1, max = 100)
    private String title;
    @Positive
    private BigDecimal price;
}
