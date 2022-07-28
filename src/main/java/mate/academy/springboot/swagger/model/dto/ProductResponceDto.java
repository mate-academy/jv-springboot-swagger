package mate.academy.springboot.swagger.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponceDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
