package mate.academy.springboot.swagger.model.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    @NonNull
    private String title;
    @NonNull
    private BigDecimal price;
}
