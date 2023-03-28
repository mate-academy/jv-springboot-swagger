package mate.academy.springboot.swagger.model.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponseDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
