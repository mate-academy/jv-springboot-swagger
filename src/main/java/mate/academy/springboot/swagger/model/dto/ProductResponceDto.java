package mate.academy.springboot.swagger.model.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductResponceDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
