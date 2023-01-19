package com.pwc.test.pwctest.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwc.test.pwctest.dto.RouteDto;
import com.pwc.test.pwctest.util.GraphSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CountryService {

    private final Map<String, List<String>> countriesGraph = new HashMap<>();
    private List<LinkedHashMap<String, Object>> countries = new ArrayList<>();

    // may be moved to application properties
    private final String countriesLink = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json";

    private final String COUNTRY_CODE_FIELD_NAME = "cca3";
    private final String COUNTRY_BORDERS_FIELD_NAME = "borders";

    public CountryService() {
        initMapList();
    }

    public RouteDto getRoute(String origin, String destination) {
        return RouteDto.builder()
                .route(GraphSearchUtil.getShortestDistance(countriesGraph, origin, destination))
                .build();
    }

    private void initMapList() {
        try {
            countries = getJsonFromUrl(new URL(countriesLink), countries.getClass());

            // adding country entries to the graph (where node/vertex is country and borders are edges)
            countries.forEach(country -> countriesGraph.put(
                    country.get(COUNTRY_CODE_FIELD_NAME).toString(),
                    (List<String>) country.get(COUNTRY_BORDERS_FIELD_NAME)
            ));
        } catch (IOException e) {
            log.error("Error occurred while trying to retrieve countries data: " + e.getMessage());
        }
    }

    private <T> T getJsonFromUrl(URL url, Class<T> type) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(url, type);
    }

}
