package mate.academy.springboot.swagger.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
