package mate.academy.springboot.swagger.domain.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
