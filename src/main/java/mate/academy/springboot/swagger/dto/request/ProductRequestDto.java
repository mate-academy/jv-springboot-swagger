package mate.academy.springboot.swagger.dto.request;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProductRequestDto {
    @Schema(name = "Product title", required = true)
    private String title;
    @Schema(name = "Product price", required = true)
    private BigDecimal price;
}
