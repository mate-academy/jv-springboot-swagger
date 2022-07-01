package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductResponseDto {
    Long id;
    String title;
    BigDecimal price;
}
