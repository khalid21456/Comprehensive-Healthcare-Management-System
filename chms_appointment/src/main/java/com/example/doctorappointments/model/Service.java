package com.example.doctorappointments.model;

public class Service {
    private int IDService;
    private String NameService;
    private String Description;

    public Service(int idService, String nameService) {
        this.IDService = idService;
        this.NameService = nameService;
    }

    public Service(Integer IDService, String nomService, String description) {
        this.IDService = IDService;
        NameService = nomService;
        this.Description = description;
    }

    public Integer getIDService() {
        return IDService;
    }

    public void setIDService(Integer IDService) {
        this.IDService = IDService;
    }

    public String getNameService() {
        return NameService;
    }

    public void setNameService(String nomService) {
        NameService = nomService;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    @Override
    public String toString() {
        return NameService; // This will display the service name in the ComboBox
    }
}
