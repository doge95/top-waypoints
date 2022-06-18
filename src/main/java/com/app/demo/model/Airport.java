package com.app.demo.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Airport implements Serializable {

    private String uid;
    private String name;
    private String iata;
    private String icao;
    private double lat;
    private double lng;
    private double alt;
}
