package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.ProductSortOrderParseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;
    private final ProductSortOrderParseService productSortOrderParseService;

    public ProductController(ProductService productService, ProductDtoMapper productDtoMapper,
                             ProductSortOrderParseService productSortOrderParseService) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
        this.productSortOrderParseService = productSortOrderParseService;
    }

    @PostMapping
    @ApiOperation(value = "add new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productDtoMapper.mapToEntity(requestDto));
        return productDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "output product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productDtoMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping()
    @ApiOperation(value = "output list of products with sorting")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "10")
                                               @ApiParam(value = "default value is 10")
                                               Integer count,
                                           @RequestParam (defaultValue = "0")
                                               @ApiParam(value = "default value is 0")
                                               Integer page,
                                           @RequestParam (defaultValue = "id")
                                               @ApiParam(value = "default sort is by id")
                                               String sortBy) {
        List<Sort.Order> orders = productSortOrderParseService.parse(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "output list of products in price range with sorting")
    public List<ProductResponseDto> getAllWherePriceBetween(
            @RequestParam (name = "from") BigDecimal lowerBound,
            @RequestParam (name = "to") BigDecimal upperBound,
            @RequestParam (defaultValue = "10")
                @ApiParam(value = "default value is 10") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = "default sort is by id") String sortBy) {
        List<Sort.Order> orders = productSortOrderParseService.parse(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(lowerBound, upperBound, pageRequest)
                .stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    @ApiOperation(value = "adding mock data")
    public String injectData() {
        Random random = new Random();
        for (int i = 1; i < 30; i++) {
            Product product = new Product();
            product.setPrice(BigDecimal.valueOf(random.nextInt(10000)));
            product.setTitle("Product " + i);
            productService.save(product);
        }
        return "Injected some data";
    }
}
