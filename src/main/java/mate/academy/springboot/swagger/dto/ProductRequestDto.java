package mate.academy.springboot.swagger.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductRequestDto {
    private String title;
    private String price;
}
