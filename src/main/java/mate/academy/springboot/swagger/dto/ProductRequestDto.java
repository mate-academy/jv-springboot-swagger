package mate.academy.springboot.swagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema
public class ProductRequestDto {
    @Schema(example = "some product")
    private String title;
    @Schema(example = "100")
    private BigDecimal price;
}
