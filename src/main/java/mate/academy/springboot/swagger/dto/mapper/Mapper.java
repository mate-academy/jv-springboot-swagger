package mate.academy.springboot.swagger.dto.mapper;

public interface Mapper<M, R, F> {
    F mapToDto(M model);

    M mapToModel(R requestDtp);
}
