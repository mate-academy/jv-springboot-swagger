package mate.academy.springboot.swagger.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRequestDto {
    private String title;
    private BigDecimal price;
}
