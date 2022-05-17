package mate.academy.springboot.swagger.model.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    @NotNull
    private String title;
    @Positive
    private BigDecimal price;
}
