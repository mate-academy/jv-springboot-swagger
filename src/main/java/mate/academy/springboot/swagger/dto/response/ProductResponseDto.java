package mate.academy.springboot.swagger.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponseDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
