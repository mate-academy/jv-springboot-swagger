package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ProductRequestDto {
    @NotNull
    private String title;
    @NotNull
    @Positive
    private BigDecimal price;
}
