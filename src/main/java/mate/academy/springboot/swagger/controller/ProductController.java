package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private ProductMapper productMapper;

    public ProductController(ProductService productService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/inject")
    @ApiOperation(value = "Inject products to DB for testing")
    public String inject() {
        productService.save(new Product("Sumsung S10", BigDecimal.valueOf(886)));
        productService.save(new Product("Sumsung S10", BigDecimal.valueOf(886)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1149)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1151)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(810)));
        productService.save(new Product("HUAWEI P10", BigDecimal.valueOf(997)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(820)));
        productService.save(new Product("iPhone 12", BigDecimal.valueOf(734)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(857)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1033)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1198)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1095)));
        productService.save(new Product("Sumsung S10", BigDecimal.valueOf(896)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1117)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(822)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1199)));
        productService.save(new Product("Sumsung S10", BigDecimal.valueOf(925)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(726)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1088)));
        productService.save(new Product("HUAWEI P10", BigDecimal.valueOf(972)));
        productService.save(new Product("HUAWEI P10", BigDecimal.valueOf(966)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1171)));
        productService.save(new Product("iPhone 12", BigDecimal.valueOf(762)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1122)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1131)));
        productService.save(new Product("HUAWEI P10", BigDecimal.valueOf(994)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1032)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1038)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1136)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(816)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(648)));
        productService.save(new Product("HUAWEI P10", BigDecimal.valueOf(964)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1062)));
        productService.save(new Product("iPhone 12", BigDecimal.valueOf(750)));
        productService.save(new Product("Sumsung S10", BigDecimal.valueOf(908)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1071)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1122)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(826)));
        productService.save(new Product("Sumsung S10", BigDecimal.valueOf(907)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(673)));
        productService.save(new Product("Sumsung S10", BigDecimal.valueOf(914)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(862)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(617)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(613)));
        productService.save(new Product("HUAWEI P10", BigDecimal.valueOf(980)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1054)));
        productService.save(new Product("Sumsung S10", BigDecimal.valueOf(888)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1043)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(732)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(833)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1075)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(653)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(725)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(637)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1124)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(614)));
        productService.save(new Product("iPhone 12", BigDecimal.valueOf(789)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(663)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1053)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(636)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(830)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1140)));
        productService.save(new Product("iPhone 12", BigDecimal.valueOf(792)));
        productService.save(new Product("Sumsung S10", BigDecimal.valueOf(886)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(717)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(695)));
        productService.save(new Product("HUAWEI P10", BigDecimal.valueOf(955)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1174)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(808)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(845)));
        productService.save(new Product("HUAWEI P10", BigDecimal.valueOf(941)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1149)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1110)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1184)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(663)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(731)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(604)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(622)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(642)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1003)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1065)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(727)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(864)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1043)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(711)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1079)));
        productService.save(new Product("Vivo v2050", BigDecimal.valueOf(1143)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1111)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(833)));
        productService.save(new Product("iPhone 12", BigDecimal.valueOf(797)));
        productService.save(new Product("HUAWEI P20", BigDecimal.valueOf(1045)));
        productService.save(new Product("iPhone 12", BigDecimal.valueOf(745)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(619)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1124)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(656)));
        productService.save(new Product("iPhone 7", BigDecimal.valueOf(605)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(864)));
        productService.save(new Product("Vivo v2020", BigDecimal.valueOf(1080)));
        productService.save(new Product("Sumsung S20", BigDecimal.valueOf(805)));
        productService.save(new Product("iPhone X", BigDecimal.valueOf(710)));
        productService.save(new Product("iPhone 12", BigDecimal.valueOf(735)));

        return "Inject done!";
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.toResponseDto(
                productService.save(productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toResponseDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteByID(id);
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "default value is '20'") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = "default value is 'id' (DESC)") String sortBy) {
        List<Sort.Order> orders = SortParser.getOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products list by price between")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal priceFrom, @RequestParam BigDecimal priceTo,
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "default value is '20'") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = "default value is 'id' (DESC)") String sortBy) {
        List<Sort.Order> orders = SortParser.getOrders(sortBy);
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(priceFrom, priceTo, pageable)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
