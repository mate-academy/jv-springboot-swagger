package mate.academy.springboot.swagger.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductResponseDto {
    private String title;
    private BigDecimal price;
}
