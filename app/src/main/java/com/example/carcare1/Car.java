package com.example.carcare1;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Car implements Serializable {
    private String make;
    private String model;
    private int car_year;
    private int test_month;
    private int insurance_month;
    private int km;
    private int car_number;
    private int lastKmBeforeGarage;
    private Map<String, Integer> mapFinanc;


    public Car( String make,
                String model,
                int car_year,
                int test_month,
                int km,
                int car_number){
        this.make =  make;
        this.car_number =car_number;
        this.car_year = car_year;
        this.km =km;
        this.test_month = test_month;
        this.model=model;
        this.mapFinanc = new HashMap<>();
    }

    public Car(){
        this.mapFinanc = new HashMap<>();
    }
    public String getModel() {
        return model;
    }

    public int getCar_year() {
        return car_year;
    }

    public int getKm() {
        return km;
    }

    public int getCar_number() {
        return car_number;
    }

    public int getTest_month() {
        return test_month;
    }
    public int getInsurance_month(){
        return insurance_month;
    }

    public String getMake() {
        return make;
    }
    public int getLastKmBeforeGarage(){
        return lastKmBeforeGarage;
    }

    public Map<String,Integer> getMapFinanc(){
        return mapFinanc;
    }
    public int getSpecificCost(String name){
        if(mapFinanc.get(name) == null){
            return 0;
        }
        return mapFinanc.get(name);
    }
    public void setLastKmBeforeGarage(int num){
        this.lastKmBeforeGarage = num;
    }
    public void setInsurance_month(int num){
        this.insurance_month = num;
    }
    public void setMapFinanc(String id, int cost){
        mapFinanc.put(id,cost);
    }

    public void setCar_number(int car_number) {
        this.car_number = car_number;
    }

    public void setCar_year(int car_year) {
        this.car_year = car_year;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setTest_month(int test_month) {
        this.test_month = test_month;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @NonNull
    @Override
    public String toString() {
        return getCar_number()+" "+getMake()+" "+ getModel();
    }
}
