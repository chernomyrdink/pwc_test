package com.pwc.test.pwctest.web;

import com.pwc.test.pwctest.dto.RouteDto;
import com.pwc.test.pwctest.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/routing")
public class RouteController {

    private final CountryService countryService;

    public RouteController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(path = "/{origin}/{destination}")
    public ResponseEntity<RouteDto> getRoute(
            @PathVariable String origin,
            @PathVariable String destination
    ) {
        // input validation
        if (!StringUtils.hasText(origin) || !StringUtils.hasText(destination)) {
            return ResponseEntity.badRequest().build();
        }
        if (origin.length() != 3 || destination.length() != 3) {
            return ResponseEntity.badRequest().build();
        }

        //get shortest route
        RouteDto result = countryService.getRoute(origin.toUpperCase(Locale.ROOT), destination.toUpperCase(Locale.ROOT));
        if (result.getRoute() == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
