package mate.academy.springboot.swagger.dto.request;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "RequestDTO for product post and put")
public class ProductRequestDto {
    @NotNull
    private String title;
    @NotNull
    private BigDecimal price;
}
