package com.example.realtimemeasurement.dto;

public class Avg {
    private double sumTemperature;

    public double getSumTemperature() {
        return sumTemperature;
    }

    public void setSumTemperature(double sumTemperature) {
        this.sumTemperature = sumTemperature;
    }

    public double getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public double getSumHumidity() {
        return sumHumidity;
    }

    public void setSumHumidity(double sumHumidity) {
        this.sumHumidity = sumHumidity;
    }

    public double getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(double avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    public int getReadingCount() {
        return readingCount;
    }

    public void setReadingCount(int readingCount) {
        this.readingCount = readingCount;
    }

    private double avgTemperature;
    private double sumHumidity;
    private double avgHumidity;
    private int readingCount;
    public Avg() {
        this.sumTemperature = 0;
        this.avgTemperature = 0;
        this.sumHumidity = 0;
        this.avgHumidity = 0;
        this.readingCount = 0;

    }
}