package com.example.doctorappointments.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Availability {
    private int IDDoc;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available;
    // Constructeur
    public Availability(int IDDoc, LocalDate date, LocalTime startTime, LocalTime endTime, boolean available) {
        this.IDDoc = IDDoc;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }

    // Getters et Setters
    public int getIDDoc() {
        return IDDoc;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return available;
    }
}
