package com.pwc.test.pwctest.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class RouteDto {

    private List<String> route;
}
