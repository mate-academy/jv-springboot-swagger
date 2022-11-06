package mate.academy.springboot.swagger.model.dto.request;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class ProductRequestDto {
    private String title;
    private BigDecimal price;
}
