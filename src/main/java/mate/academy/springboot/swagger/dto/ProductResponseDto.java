package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductResponseDto {

    private Long id;
    private BigDecimal price;
    private String title;
}
