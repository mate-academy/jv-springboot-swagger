package mate.academy.springboot.swagger.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@ApiModel(value = "Request product")
public class ProductRequestDto {
    @NotNull
    @ApiModelProperty(value = "title of product", example = "IPhone 10")
    private String title;
    @DecimalMin(value = "0.0", inclusive = false)
    @ApiModelProperty(value = "price of product", example = "999.99")
    private BigDecimal price;
}
