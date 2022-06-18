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
    public ArrayList<HashMap<String, Integer>> getTopWaypoints(@RequestParam String icao, @RequestParam(required = false) String sid, @RequestParam(required = false) String star){
        ArrayList<HashMap<String, Integer>> results =  new ArrayList<>();

        String type = "sids";
        List<Procedure> sidList = apiService.getProceduresByICAO(icao, type, sid);

        type = "stars";
        List<Procedure> starList = apiService.getProceduresByICAO(icao, type, star);

        HashMap<String, Integer> sid_stats = getMaxWaypoint(sidList);
        HashMap<String, Integer> star_stats = getMaxWaypoint(starList);

        results.add(sid_stats);
        results.add(star_stats);

        return results;
    }


    // get top 2 greatest values
    public HashMap<String, Integer> getMaxWaypoint(List<Procedure> procedureList){
        HashMap<String, Integer> results = new HashMap<>();

        HashMap<String, Integer> counts = countWaypoints(procedureList);

        for (int i = 0; i < TOP_WAYPOINTS; i++) {
            if (results.size() < 2){
                int maxValueInMap = (Collections.max(counts.values()));

                for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                    String name = entry.getKey();
                    if (entry.getValue() == maxValueInMap) {
                        results.put(name, maxValueInMap);
                    }
                }
                counts.keySet().removeAll(results.keySet());
            }
        }

        return results;
    }

    // count number of presences for each waypoint
    public HashMap<String, Integer> countWaypoints(List<Procedure> procedureList){
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
