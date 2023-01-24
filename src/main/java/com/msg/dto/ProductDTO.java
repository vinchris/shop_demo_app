package com.msg.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class ProductDTO {

    private String description;
    private String productStatus;
    private List<String> categories;
}
