package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String name;
    private BigDecimal price;
}
