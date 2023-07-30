package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;

public class ProductResponseDto {
    private Long id;
    private String title;
    private BigDecimal price;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
