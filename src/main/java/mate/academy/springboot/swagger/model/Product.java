package mate.academy.springboot.swagger.model;

import java.math.BigDecimal;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private BigDecimal price;
}
