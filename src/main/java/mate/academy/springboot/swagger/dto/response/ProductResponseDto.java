package mate.academy.springboot.swagger.dto.response;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductResponseDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
