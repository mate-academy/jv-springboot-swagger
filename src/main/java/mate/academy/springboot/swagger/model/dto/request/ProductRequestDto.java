package mate.academy.springboot.swagger.model.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ProductRequestDto {
    @NotBlank
    private String title;
    @Positive
    private BigDecimal price;
}
