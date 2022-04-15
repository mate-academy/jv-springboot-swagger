package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SortService sortService;
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto add(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        productService.save(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                      @RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Get all product list")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
                                                @ApiParam(value = "Default value is `20`")
                                                        Integer count,
                                            @RequestParam (defaultValue = "0")
                                                @ApiParam(value = "Default value is `0`")
                                                        Integer page,
                                            @RequestParam (defaultValue = "id")
                                                @ApiParam(value = "Default value is `id`")
                                                        String sortBy) {
        Sort sort = Sort.by(sortService.parseSortingCondition(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price/between")
    @ApiOperation(value = "Get product list between price")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam (defaultValue = "20")
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
        Sort sort = Sort.by(sortService.parseSortingCondition(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    @ApiOperation("Inject products for testing API")
    public String injector() {
        Product cherry = new Product();
        cherry.setTitle("cherry");
        cherry.setPrice(BigDecimal.valueOf(150));
        productService.save(cherry);
        Product onion = new Product();
        onion.setTitle("onion");
        onion.setPrice(BigDecimal.valueOf(40));
        productService.save(onion);
        Product sausage = new Product();
        sausage.setTitle("sausage");
        sausage.setPrice(BigDecimal.valueOf(450));
        productService.save(sausage);
        Product vine = new Product();
        vine.setTitle("vine");
        vine.setPrice(BigDecimal.valueOf(450));
        productService.save(vine);
        return "Done";
    }
}
