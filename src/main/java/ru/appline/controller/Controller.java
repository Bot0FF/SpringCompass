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
        if(sidesRequest.size() != 8) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Должно быть 8 сторон света!"); }
        sides.clear();
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
                break;
            } else result = "North";
        }

        return Collections.singletonMap("Side", result);
    }
}

/* Side
{
"North": "345-15",
"North-East": "16-90",
"East": "91-135",
"South-East": "136-180",
"South": "181-225",
"South-West": "226-270",
"West": "271-315",
"North-West": "316-344"
}
 */
