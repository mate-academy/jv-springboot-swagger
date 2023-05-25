package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductResponseDto;
import mate.academy.springboot.swagger.dto.response.ProductRequestDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mappers.ProductMapper;
import mate.academy.springboot.swagger.sorter.MyCustomSorter;
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
    private final ProductMapper productMapper;
    private final MyCustomSorter myCustomSorter;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             MyCustomSorter myCustomSorter) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.myCustomSorter = myCustomSorter;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.mapToModel(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);
        Product save = productService.save(product);
        return productMapper.mapToDto(save);
    }

    @GetMapping("/inject")
    @ApiOperation(value = "Inject initial data")
    public String inject() {
        Product iphone = new Product();
        iphone.setTitle("iPhone 12");
        iphone.setPrice(BigDecimal.valueOf(1999));
        productService.save(iphone);

        Product samsung = new Product();
        samsung.setTitle("Samsung s20");
        samsung.setPrice(BigDecimal.valueOf(1500));
        productService.save(samsung);

        Product ps5 = new Product();
        ps5.setTitle("PS5");
        ps5.setPrice(BigDecimal.valueOf(900));
        productService.save(ps5);

        Product ecoflow = new Product();
        ecoflow.setTitle("EcoFlow");
        ecoflow.setPrice(BigDecimal.valueOf(1100));
        productService.save(ecoflow);

        Product sword = new Product();
        sword.setTitle("Medieval Sword from France XII");
        sword.setPrice(BigDecimal.valueOf(1000));
        productService.save(sword);

        return "Done!";

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.get(id);
        return productMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Find all products by price between (with pagination and sorting)")
    public List<ProductResponseDto> findAllByPrice(
            @RequestParam BigDecimal from, @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "sorts by id by default") String sortBy,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is 20") Integer count,
            @RequestParam (defaultValue = "0") Integer page) {
        List<Sort.Order> sortingOrders = myCustomSorter.getSortingOrders(sortBy);
        Sort sort = Sort.by(sortingOrders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "Find all products (with pagination and sorting)")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is 20") Integer count,
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "sorts by id by default") String sortBy) {
        List<Sort.Order> sortingOrders = myCustomSorter.getSortingOrders(sortBy);
        Sort sort = Sort.by(sortingOrders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);

        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());

    }
}
