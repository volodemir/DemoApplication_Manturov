package ru.manturov.api.converter;

import org.springframework.stereotype.Component;

@Component
public interface Converter<S, T> {
    T convert(S source);
}
