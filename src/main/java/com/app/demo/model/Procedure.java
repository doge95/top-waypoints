package com.app.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Procedure implements Serializable {

    private String name;
    private Airport airport;
    private List<Waypoint> waypoints;
}
