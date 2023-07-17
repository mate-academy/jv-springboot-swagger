package mate.academy.springboot.swagger.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(
            value = "product name",
            name = "title",
            dataType = "String",
            example = "Coca cola 0.5l")
    private String title;
    @ApiModelProperty(
            value = "product price in BigDecimal format",
            name = "price",
            dataType = "BigDecimal",
            example = "123.00")
    private BigDecimal price;
}
