package mate.academy.springboot.swagger.dto.response;

import javax.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class ProductResponseDto {
    @NonNull
    private Long id;
    @NonNull
    private String title;
    @Min(0)
    private Double price;
}
