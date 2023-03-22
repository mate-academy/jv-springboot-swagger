package mate.academy.springboot.swagger.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private String title;
    private BigDecimal price;
}
