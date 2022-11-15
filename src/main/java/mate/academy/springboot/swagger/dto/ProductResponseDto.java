package mate.academy.springboot.swagger.dto;

import lombok.Data;

@Data
public class ProductResponseDto {
    private long id;
    private String title;
    private double price;
}
