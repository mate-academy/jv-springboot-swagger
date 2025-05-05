package mate.academy.springboot.swagger.dto;

import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private Double price;
}
