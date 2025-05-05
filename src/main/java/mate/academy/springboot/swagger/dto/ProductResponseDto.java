package mate.academy.springboot.swagger.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "Response product")
public class ProductResponseDto {
    @ApiModelProperty(value = "unique identifier of product", example = "10")
    private Long id;
    @ApiModelProperty(value = "title of product", example = "IPhone 10")
    private String title;
    @ApiModelProperty(value = "price of product", example = "999.99")
    private BigDecimal price;
}
