package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class ProductRequestDto {
    private String title;
    private BigDecimal price;
}
