package Catalog;

public class MateriePersoana {

    private String materie;
    String contor = "contor";
    private Elev elev = new Elev(contor);

    public MateriePersoana() {
        materie = "";
        elev = new Elev(contor);
    }

    public MateriePersoana(String materie, Elev elev) {
        this.materie = materie;
        this.elev.nume = elev.nume;
        this.elev.prenume = elev.prenume;
        this.elev.initialaTatalui = elev.initialaTatalui;
        this.elev.cnp = elev.cnp;
        this.elev.nrTelefon = elev.nrTelefon;
    }

    // getters
    public String getMaterie() {
        return materie;
    }
    public String getNumeComplet() {
        return elev.numeComplet();
    }
}
