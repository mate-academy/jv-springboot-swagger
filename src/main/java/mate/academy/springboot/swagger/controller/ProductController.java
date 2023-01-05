package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper mapper;

    public ProductController(ProductService productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.add(mapper.dtoToModel(requestDto));
        return mapper.modelToDto(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return mapper.modelToDto(product);
    }

    @GetMapping
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "20") Integer size,
                                           @RequestParam(defaultValue = "title") String orderBy) {
        Sort sort = productService.getSorter(orderBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAll(pageRequest).stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal priceFrom,
                                                  @RequestParam BigDecimal priceTo,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "20") Integer size,
                                                  @RequestParam(defaultValue = "price") String orderBy) {
        Sort sort = productService.getSorter(orderBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAllBetweenPrice(priceFrom, priceTo, pageRequest).stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ProductRequestDto requestDto) {
        Product product = mapper.dtoToModel(requestDto);
        product.setId(id);
        productService.update(product);
    }

    @GetMapping("/inject")
    public String inject() {

        Product tv = new Product();
        tv.setTitle("LG");
        tv.setPrice(BigDecimal.valueOf(1111.11));

        Product phone = new Product();
        phone.setTitle("iPhone");
        phone.setPrice(BigDecimal.valueOf(999.99));

        Product ps = new Product();
        ps.setTitle("PS 5");
        ps.setPrice(BigDecimal.valueOf(555.55));

        productService.add(tv);
        productService.add(phone);
        productService.add(ps);

        return "Data injected " + LocalTime.now();
    }
}
