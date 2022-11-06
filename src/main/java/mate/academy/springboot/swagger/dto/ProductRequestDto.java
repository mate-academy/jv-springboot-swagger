package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ProductRequestDto {
    @NotNull
    @Size(max = 50)
    private String title;
    @Positive
    private BigDecimal price;
}
