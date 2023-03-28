package ru.appline.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private Map<String, String> sides = new HashMap<>();

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    private Map<String, String> side(@RequestBody Map<String, String> sidesRequest) {
        if(sidesRequest.size() != 4) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Должно быть 4 стороны света!"); }
        sides.putAll(sidesRequest);
        return sides;
    }

    @GetMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> checkSide(@RequestBody String degree) {
        int number = Integer.parseInt(degree.replaceAll("\\D+",""));
        return findSideByDegree(number);
    }

    private Map<String, String> findSideByDegree(Integer degree) {
        String result = null;
        for (Map.Entry<String, String> entry : sides.entrySet()) {
            String[] numbers = entry.getValue().split("-");
            if (degree >= Integer.parseInt(numbers[0]) && degree <= Integer.parseInt(numbers[1])) {
                result = entry.getKey();
            }
        }

        return Collections.singletonMap("Side", result);
    }
}
