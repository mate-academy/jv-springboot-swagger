package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ProductResponseDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
