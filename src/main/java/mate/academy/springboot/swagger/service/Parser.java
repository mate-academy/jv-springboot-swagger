package mate.academy.springboot.swagger.service;

import java.util.List;

public interface Parser<O, V> {
    List<O> parse(V value);
}
