package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "Title is mandatory")
    @Size(min = 5, max = 155)
    private String title;
    @NonNull
    @Min(value = 1,message = "Must be more than 1")
    private BigDecimal price;
}
