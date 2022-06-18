package com.app.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Waypoint implements Serializable {

    private String uid;
    private String name;
    private double lat;
    private double lng;
}
