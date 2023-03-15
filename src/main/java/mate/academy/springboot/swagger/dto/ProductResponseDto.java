package mate.academy.springboot.swagger.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String title;
    private String price;
}
