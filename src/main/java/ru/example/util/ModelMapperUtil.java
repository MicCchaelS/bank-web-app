package ru.example.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelMapperUtil {
    private final ModelMapper modelMapper;

    public <D, S> D map(S source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }
}