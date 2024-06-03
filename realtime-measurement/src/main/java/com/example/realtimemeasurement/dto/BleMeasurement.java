package com.example.realtimemeasurement.dto;
import java.util.List;

public class BleMeasurement {

    private BleReporter reporter;
    private List<Measurement> measurements;

    public BleMeasurement() {
    }

    public BleMeasurement(BleReporter reporter, List<Measurement> measurements) {
        this.reporter = reporter;
        this.measurements = measurements;
    }

    public BleReporter getReporter() {
        return reporter;
    }
    public void setReporter(BleReporter reporter) {
        this.reporter = reporter;
    }
    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public static class Data{
        private double temperature;
        private double humidity;

        public Data() {
        }

        public Data(double temperature, double humidity) {
            this.temperature = temperature;
            this.humidity = humidity;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }
    }

    public static class Measurement{
        private String type;
        private String deviceAddress;
        private int rssi;

        public Measurement() {
        }

        public Measurement(String type, String deviceAddress, int rssi, Data data) {
            this.type = type;
            this.deviceAddress = deviceAddress;
            this.rssi = rssi;
            this.data = data;
        }

        private Data data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeviceAddress() {
            return deviceAddress;
        }

        public void setDeviceAddress(String deviceAddress) {
            this.deviceAddress = deviceAddress;
        }

        public int getRssi() {
            return rssi;
        }

        public void setRssi(int rssi) {
            this.rssi = rssi;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public static class BleReporter{
        private String name;
        private String identifier;
        private String ipv4;
        private String ipv6;
        private String hwType;

        public BleReporter() {
        }

        public BleReporter(String name, String identifier, String ipv4, String ipv6, String hwType, String firmwareVersion) {
            this.name = name;
            this.identifier = identifier;
            this.ipv4 = ipv4;
            this.ipv6 = ipv6;
            this.hwType = hwType;
            this.firmwareVersion = firmwareVersion;
        }

        private String firmwareVersion;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getIpv4() {
            return ipv4;
        }

        public void setIpv4(String ipv4) {
            this.ipv4 = ipv4;
        }

        public String getIpv6() {
            return ipv6;
        }

        public void setIpv6(String ipv6) {
            this.ipv6 = ipv6;
        }

        public String getHwType() {
            return hwType;
        }

        public void setHwType(String hwType) {
            this.hwType = hwType;
        }

        public String getFirmwareVersion() {
            return firmwareVersion;
        }

        public void setFirmwareVersion(String firmwareVersion) {
            this.firmwareVersion = firmwareVersion;
        }
    }


}
