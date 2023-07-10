package mate.academy.springboot.swagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema
public class ProductRequestDto {
    @Schema(example = "some product")
    @NonNull
    private String title;
    @Schema(example = "100")
    @NonNull
    private BigDecimal price;
}
