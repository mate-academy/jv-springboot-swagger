package mate.academy.springboot.swagger.dto.response;

import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private Long price;
}
