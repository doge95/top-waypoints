package com.app.demo.controller;

import com.app.demo.model.*;
import com.app.demo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Value("${top.waypoints}")
    private int TOP_WAYPOINTS;

    @GetMapping("/getAirports")
    public List<Airport> getAirports(){
        return apiService.getAllAirports();
    }

    @GetMapping("/getTopWaypoints")
    private List<Statistic> getTopWaypoints(@RequestParam String icao, @RequestParam String type){
        List<Statistic> results = new ArrayList<>();
        List<Procedure> procedureList = apiService.getProceduresByICAO(icao, type);

        if (!(procedureList == null || procedureList.isEmpty())) {
            results = getMaxWaypoint(procedureList);
        }

        return results;

    }


    // get top 2 greatest values
    private List<Statistic> getMaxWaypoint(List<Procedure> procedureList){
        List<Statistic> results = new ArrayList<>();

        HashMap<String, Integer> counts = countWaypoints(procedureList);

        for (int i = 0; i < TOP_WAYPOINTS; i++) {
            if (results.size() < 2){
                int maxValueInMap = (Collections.max(counts.values()));
                HashMap<String, Integer> intermediate_results = new HashMap<>();

                for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                    String name = entry.getKey();
                    if (entry.getValue().intValue() == maxValueInMap) {
                        Statistic stat = new Statistic();
                        stat.setName(name);
                        stat.setCount(maxValueInMap);
                        results.add(stat);
                        intermediate_results.put(name, maxValueInMap);
                    }
                }
                counts.keySet().removeAll(intermediate_results.keySet());
            }
        }

        return results;
    }

    // count number of presences for each waypoint
    private HashMap<String, Integer> countWaypoints(List<Procedure> procedureList){
        HashMap<String, Integer> results = new HashMap<>();

        for (Procedure procedure: procedureList) {
            for (Waypoint waypoint: procedure.getWaypoints()) {
                String name = waypoint.getName();
                if (results.get(name) == null) {
                    results.put(name, 1);
                } else {
                    results.put(name, results.get(name) + 1);
                }
            }
        }

        return results;
    }

}
