package mate.academy.springboot.swagger.mapper;

public interface Mapper<M,R,A> {
    M toModel(R dto);

    A toDto(M model);
}
