package com.example.doctorappointments.model;

public class Speciality {
    private int idSpeciality;
    private String nomSpeciality;

    public Speciality(int idSpeciality, String nomSpeciality) {
        this.idSpeciality = idSpeciality;
        this.nomSpeciality = nomSpeciality;
    }

    public int getIdSpeciality() {
        return idSpeciality;
    }

    public String getNomSpeciality() {
        return nomSpeciality;
    }

    @Override
    // public String toString() {
        //return "Speciality{" +
              //  "idSpeciality=" + idSpeciality +
               // ", nomSpeciality='" + nomSpeciality + '\'' +
             //   '}';
   // }
    public String toString() {
        return nomSpeciality;
    }
}