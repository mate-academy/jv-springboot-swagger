package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductMapper;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setTitle(productRequestDto.getTitle());
        product.setPrice(productRequestDto.getPrice());
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public void update(Long id, ProductRequestDto productRequestDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product newProduct = optionalProduct.get();
            newProduct.setPrice(productRequestDto.getPrice());
            newProduct.setTitle(productRequestDto.getTitle());
            productRepository.save(newProduct);
        }
    }

    @Override
    public ProductResponseDto getById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return productMapper.toDto(optionalProduct.get());
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDto> findAll(Integer page, Integer size, String sortBy) {
        Pageable pageRequest = Parser.getPageRequest(page, size, sortBy);
        return productRepository.findAll(pageRequest)
                                .stream()
                                .map(productMapper::toDto)
                                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getAllByPriceBetween(BigDecimal from, BigDecimal to,
                                                         Integer page, Integer size,
                                                         String sortBy) {
        Pageable pageRequest = Parser.getPageRequest(page, size, sortBy);
        return productRepository.findAllByPriceBetween(from, to, pageRequest)
                                .stream()
                                .map(productMapper::toDto)
                                .collect(Collectors.toList());
    }
}
