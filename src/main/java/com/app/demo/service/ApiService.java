package com.app.demo.service;

import com.app.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ApiService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    // inject value from application.properties
    @Value("${api.url}")
    private String API_URL;

    @Value("${api.key}")
    private String API_KEY;
    public List<Airport> getAllAirports() {

        String url = API_URL + "/airports";

        HttpHeaders headers = new HttpHeaders();
        headers.add("api-key", API_KEY);
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        ResponseEntity<List<Airport>> result = restTemplateBuilder.build().
                exchange(url,
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<Airport>>() {});

        List<Airport> airportList = new ArrayList<>();
        airportList.addAll(result.getBody());

        return airportList;
    }

    public List<Procedure> getProceduresByICAO(String icao, String type) {

        String url = API_URL + "/" + type + "/airport/" + icao;

        HttpHeaders headers = new HttpHeaders();
        headers.add("api-key", API_KEY);
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        ResponseEntity<List<Procedure>> result = restTemplateBuilder.build().
                exchange(url,
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<Procedure>>() {});

        List<Procedure> procedureList = new ArrayList<>();
        procedureList.addAll(result.getBody());

        return procedureList;
    }

}
