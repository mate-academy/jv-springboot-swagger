package mate.academy.springboot.swagger.model.dto;

import java.math.BigDecimal;

public class ProductRequestDto {
    private String title;
    private BigDecimal price;

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
