package com.app.demo.controller;

import com.app.demo.model.*;
import com.app.demo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/getAirports")
    public List<Airport> getAirports(){
        return apiService.getAllAirports();
    }

    @GetMapping("/getTopWaypoints")
    public List<Airport> getTopWaypoints(@RequestParam String icao, @RequestParam(required = false) String sid, @RequestParam(required = false) String star){

        String type = "sid";
        List<Procedure> sidList = apiService.getProceduresByICAO(icao, type, sid);

        type = "star";
        List<Procedure> starList = apiService.getProceduresByICAO(icao, type, star);

        return
    }

    public List<Airport> count(List<Procedure> procedureList){



        return
    }
}
