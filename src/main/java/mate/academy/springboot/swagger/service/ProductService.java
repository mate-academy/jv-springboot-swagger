package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;

public interface ProductService {
    ProductResponseDto create(ProductRequestDto productRequestDto);

    void update(Long id, ProductRequestDto productRequestDto);

    ProductResponseDto getById(Long id);

    void deleteById(Long id);

    List<ProductResponseDto> findAll(Integer page, Integer size, String sortType);

    List<ProductResponseDto> getAllByPriceBetween(BigDecimal from, BigDecimal to,
                                                  Integer page, Integer size, String sortBy);
}
