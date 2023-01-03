package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDto {
    private String title;
    private BigDecimal price;
}
