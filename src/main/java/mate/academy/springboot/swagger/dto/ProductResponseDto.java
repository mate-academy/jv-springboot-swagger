package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
