package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductRequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ProductResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortParserUtil;
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
    private ProductService productService;
    private ProductResponseDtoMapper responseDtoMapper;
    private ProductRequestDtoMapper requestDtoMapper;

    public ProductController(ProductService productService,
                             ProductResponseDtoMapper responseDtoMapper,
                             ProductRequestDtoMapper requestDtoMapper) {
        this.productService = productService;
        this.responseDtoMapper = responseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        return responseDtoMapper.mapToDto(productService.save(requestDtoMapper
                    .mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                    @RequestBody ProductRequestDto requestDto) {
        Product product = requestDtoMapper.mapToModel(requestDto);
        product.setId(id);
        return responseDtoMapper.mapToDto(productService.update(product));
    }

    @GetMapping("/price")
    @ApiOperation(value = "get all products where price between two "
            + "numbers and sort it in required order")
    public List<ProductResponseDto> getAllWherePriceBetween(@RequestParam BigDecimal from,
                                                            @RequestParam BigDecimal to,
                                                            @RequestParam (defaultValue = "20")
                                                                @ApiParam(value
                                                                        = "default value = 20")
                                                                Integer count,
                                                            @RequestParam (defaultValue = "0")
                                                                @ApiParam(value
                                                                        = "default value = 0")
                                                                Integer page,
                                                            @RequestParam (defaultValue = "id")
                                                                @ApiParam(value
                                                                        = "default sort by id")
                                                                String sortBy) {
        Sort orders = SortParserUtil.sortParse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, orders);
        return productService.getAllWherePriceBetween(from, to, pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation("get all products with pagination and sorted")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
                                                @ApiParam(value = "default value = 20")
                                                Integer count,
                                            @RequestParam (defaultValue = "0")
                                                @ApiParam(value = "default value = 0")
                                                Integer page,
                                            @RequestParam (defaultValue = "id")
                                                @ApiParam(value = "default sort by id")
                                                String sortBy) {
        Sort orders = SortParserUtil.sortParse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, orders);
        return productService.getAll(pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
