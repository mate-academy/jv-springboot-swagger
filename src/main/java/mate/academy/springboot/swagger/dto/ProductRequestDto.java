package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductRequestDto {
    @NotNull
    private String title;
    @Min(0)
    private BigDecimal price;
}
