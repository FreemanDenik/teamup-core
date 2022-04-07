package ru.team.up.sup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.dto.SupParameterDto;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("parameters")
public class ParameterController {
    @GetMapping("/core")
    public List<SupParameterDto<?>> sendJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        List<SupParameterDto<?>> list = List.of();
        try {
            list = Arrays.asList(mapper.readValue(new File("./Parameters.json"),
                    SupParameterDto[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
