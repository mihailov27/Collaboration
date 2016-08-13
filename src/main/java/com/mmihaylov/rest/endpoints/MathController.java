package com.mmihaylov.rest.endpoints;

import com.mmihaylov.rest.services.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/math")
public class MathController {

    private MathService mathService;

    @Autowired
    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    /**
     * Returns the square of the sent number.
     * @param number
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/square")
    public @ResponseBody Integer square(@RequestParam(value = "number", defaultValue = "0") Integer number) {
        Integer square = mathService.square(number);
        return square;
    }
}
