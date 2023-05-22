package mate.academy.springboot.swagger.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProductDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
