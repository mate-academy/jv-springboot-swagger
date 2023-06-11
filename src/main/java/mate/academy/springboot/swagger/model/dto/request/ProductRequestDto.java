package mate.academy.springboot.swagger.model.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequestDto {
    private String title;
    private BigDecimal price;
}
