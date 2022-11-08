package mate.academy.springboot.swagger.dto.request;

import java.math.BigDecimal;

public class ProductRequestDto {
    String title;
    BigDecimal price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
