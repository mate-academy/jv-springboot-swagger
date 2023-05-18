package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.dto.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.utility.UrlParser;
import org.springframework.data.domain.PageRequest;
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
    private ProductService productService;
    private RequestDtoMapper<Product, ProductRequestDto> requestDtoMapper;
    private ResponseDtoMapper<Product, ProductResponseDto> responseDtoMapper;

    public ProductController(ProductService productService,
                             RequestDtoMapper<Product, ProductRequestDto> requestDtoMapper,
                             ResponseDtoMapper<Product, ProductResponseDto> responseDtoMapper) {
        this.productService = productService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        return responseDtoMapper.mapToDto(productService.save(
               requestDtoMapper.mapToModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@RequestBody ProductRequestDto requestDto,
                                     @PathVariable Long id) {
        Product product = productService.update(
                requestDtoMapper.mapToModel(requestDto), id);
        return responseDtoMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    @ApiOperation(value = "get products list")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "2")
            @ApiParam(value = "default value is '2'") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is 'id'") String sortBy) {
        PageRequest pageRequest = UrlParser.formPageRequest(count,page,sortBy);
        return productService.findAll(pageRequest).stream()
                .map(e -> responseDtoMapper.mapToDto(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/find-by-price")
    @ApiOperation(value = "get list of products where prices are between")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "8")
            @ApiParam(value = "default value is '8'") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is 'id'") String sortBy) {
        PageRequest pageRequest = UrlParser.formPageRequest(count,page,sortBy);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(e -> responseDtoMapper.mapToDto(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    @ApiOperation(value = "Inject products to DB. Must be done first.")
    public String inject() {
        Product product1 = new Product();
        product1.setTitle("Shark");
        product1.setPrice(new BigDecimal(150));

        Product product2 = new Product();
        product2.setTitle("Okyn");
        product2.setPrice(new BigDecimal(200));

        Product product3 = new Product();
        product3.setTitle("Dorado");
        product3.setPrice(new BigDecimal(250));

        Product product4 = new Product();
        product4.setTitle("Lamb");
        product4.setPrice(new BigDecimal(100));

        Product product5 = new Product();
        product5.setTitle("Beaf");
        product5.setPrice(new BigDecimal(175));

        Product product6 = new Product();
        product6.setTitle("Chicken");
        product6.setPrice(new BigDecimal(99));

        Product product7 = new Product();
        product7.setTitle("Pork");
        product7.setPrice(new BigDecimal(220));

        Product product8 = new Product();
        product8.setTitle("Rabbit");
        product8.setPrice(new BigDecimal(250));

        productService.save(product1);
        productService.save(product2);
        productService.save(product3);
        productService.save(product4);
        productService.save(product5);
        productService.save(product6);
        productService.save(product7);
        productService.save(product8);
        return "Done!!!!";
    }
}
