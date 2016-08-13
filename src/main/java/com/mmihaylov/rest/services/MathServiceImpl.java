package com.mmihaylov.rest.services;

import org.springframework.stereotype.Service;

/**
 * Created by mmihaylov on 8/12/16.
 */
@Service
public class MathServiceImpl implements MathService {

    @Override
    public Integer square(Integer number) {
        Integer square = number * number;
        return square;
    }
}
