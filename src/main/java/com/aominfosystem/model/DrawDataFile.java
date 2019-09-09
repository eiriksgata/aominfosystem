package com.aominfosystem.model;

public class DrawDataFile {

    private double probability;//概率
    private String describe;//描述

    public String getDescribe() {
        return describe;
    }

    public double getProbability() {
        return probability;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
