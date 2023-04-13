package mate.academy.springboot.swagger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductRequestDto {
    private String title;
    private BigDecimal price;
}
