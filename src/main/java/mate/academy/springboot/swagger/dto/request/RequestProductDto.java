package mate.academy.springboot.swagger.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestProductDto {
    @NotEmpty(message = "The title couldn't be empty")
    private String title;
    @NotNull
    @Min(0)
    private BigDecimal price;
}
