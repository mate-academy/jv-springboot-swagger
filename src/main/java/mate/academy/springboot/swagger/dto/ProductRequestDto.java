package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequestDto {

    private BigDecimal price;
    private String title;
}
