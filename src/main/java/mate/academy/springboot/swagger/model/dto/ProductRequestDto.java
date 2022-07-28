package mate.academy.springboot.swagger.model.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductRequestDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
