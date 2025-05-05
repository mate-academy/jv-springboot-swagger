package mate.academy.springboot.swagger.dto.request;

import lombok.Data;

@Data
public class ProductRequestDto {
    private String title;
    private Long price;
}
