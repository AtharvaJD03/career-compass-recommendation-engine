package com.careercompass.careercompassbackendv2.model;

import java.util.List;

public class CareerVector {

    private String job_role;
    private List<Double> vector;

    public String getJob_role() {
        return job_role;
    }

    public void setJob_role(String job_role) {
        this.job_role = job_role;
    }

    public List<Double> getVector() {
        return vector;
    }

    public void setVector(List<Double> vector) {
        this.vector = vector;
    }
}
