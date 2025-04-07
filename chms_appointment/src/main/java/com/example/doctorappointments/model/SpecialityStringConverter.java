package com.example.doctorappointments.model;  // Place this in the same package as your models

import javafx.util.StringConverter;

public class SpecialityStringConverter extends StringConverter<Speciality> {
    @Override
    public String toString(Speciality speciality) {
        return speciality.getNomSpeciality();  // Only display the specialty name
    }

    @Override
    public Speciality fromString(String string) {
        return null;  // This is usually not needed for your use case
    }
}
