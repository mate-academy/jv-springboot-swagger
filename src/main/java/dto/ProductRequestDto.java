package dto;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String title;
    private BigDecimal price;
}
