package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequestDto {
    private String tittle;
    private BigDecimal price;
}
