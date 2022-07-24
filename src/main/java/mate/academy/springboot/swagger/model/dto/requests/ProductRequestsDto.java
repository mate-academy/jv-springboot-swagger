package mate.academy.springboot.swagger.model.dto.requests;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequestsDto {
    private String title;
    private BigDecimal price;
}
