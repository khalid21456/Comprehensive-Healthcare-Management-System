package com.example.doctorappointments.model;

public class DoctorDTO {
    private int id;
    private String name;
    private String speciality;

    public DoctorDTO(int id, String name, String speciality) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
    }

    public DoctorDTO() {
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialty(String specialty) {
        this.speciality = specialty;
    }
}
