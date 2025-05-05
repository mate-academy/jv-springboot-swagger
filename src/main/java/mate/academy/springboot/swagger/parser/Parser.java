package mate.academy.springboot.swagger.parser;

public interface Parser<T, I> {
    T parse(I i);
}
