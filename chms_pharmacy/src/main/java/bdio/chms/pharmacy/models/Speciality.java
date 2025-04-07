package bdio.chms.pharmacy.models;

public class Speciality {
    private int id;
    private String name;

    public Speciality(int id, String name) {
        this.id = id;
        this.name = name;
    }
    //constructeur utilise pour affiche specialite du docteur dans l'ordonnance
    public Speciality( String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name ;
    }
}
