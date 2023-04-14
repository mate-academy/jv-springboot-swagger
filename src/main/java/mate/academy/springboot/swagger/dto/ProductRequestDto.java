package mate.academy.springboot.swagger.dto;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequestDto {
    @ApiModelProperty(example = "T-shirt")
    private String title;
    @ApiModelProperty(example = "15.99")
    private BigDecimal price;
}
