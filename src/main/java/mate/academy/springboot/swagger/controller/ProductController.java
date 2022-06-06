package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
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
    private final ProductDtoMapper productDtoMapper;
    private final SortingService sortRequestHandler;

    public ProductController(ProductService productService,
                             ProductDtoMapper productDtoMapper,
                             SortingService sortRequestHandler) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
        this.sortRequestHandler = sortRequestHandler;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productDtoMapper.mapToDto(productService.get(id));
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        return productDtoMapper.mapToDto(productService
                .save(productDtoMapper.mapToModel(requestDto)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productDtoMapper.mapToModel(requestDto);
        product.setId(id);
        return productDtoMapper.mapToDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                               @ApiParam(value = "Default value "
                                                       + "is `20`")
                                                       Integer count,
                                           @RequestParam (defaultValue = "0")
                                           @ApiParam(value = "Default value "
                                                   + "is `0`")
                                                       Integer page,
                                           @RequestParam (defaultValue = "id")
                                               @ApiParam(value = "Default value "
                                                       + "is `id`")
                                                       String sortBy) {
        Sort sort = Sort.by(sortRequestHandler.parseSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all product in price range")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @RequestParam(defaultValue = "20")
                                                             @ApiParam(value = "Default value "
                                                                     + "is `20`")
                                                                     Integer count,
                                                         @RequestParam (defaultValue = "0")
                                                             @ApiParam(value = "Default value "
                                                                     + "is `0`")
                                                                     Integer page,
                                                         @RequestParam (defaultValue = "id")
                                                             @ApiParam(value = "Default value "
                                                                     + "is `id`")
                                                                     String sortBy) {
        Sort sort = Sort.by(sortRequestHandler.parseSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    @ApiOperation("Inject products for testing")
    public String injector() {
        Product apple = new Product();
        apple.setTitle("apple");
        apple.setPrice(BigDecimal.valueOf(123));
        productService.save(apple);

        Product pineapple = new Product();
        pineapple.setTitle("pineapple");
        pineapple.setPrice(BigDecimal.valueOf(45));
        productService.save(pineapple);

        Product cherry = new Product();
        cherry.setTitle("cherry");
        cherry.setPrice(BigDecimal.valueOf(213));
        productService.save(cherry);

        Product strawberry = new Product();
        strawberry.setTitle("strawberry");
        strawberry.setPrice(BigDecimal.valueOf(678));
        productService.save(strawberry);

        Product raspberry = new Product();
        raspberry.setTitle("raspberry");
        raspberry.setPrice(BigDecimal.valueOf(15));
        productService.save(raspberry);

        return "Injected products!";
    }
}
