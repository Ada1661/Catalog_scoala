package Catalog;

import java.util.Scanner;

public class Persoana {
    protected String nume;
    protected String prenume;
    protected String initialaTatalui;
    protected String cnp;
    protected String nrTelefon;

    public Persoana() {
        nume = prenume = initialaTatalui = cnp = nrTelefon = "";
    }

    public Persoana(int var) {
        Scanner input = new Scanner(System.in);

        System.out.println("!!!Introduceti urmatoarele date personale!!!");
        System.out.print("Numele de familie: ");
        this.nume = input.nextLine();

        System.out.print("Prenumele: ");
        this.prenume = input.nextLine();

        System.out.print("Initiala tatalui: ");
        String initialaTataluiNeverificata = input.nextLine();
        while (!verificareInitialaTatalui(initialaTataluiNeverificata)) {
            System.out.println("INVALID!");
            System.out.print("Reintroduceti initiala tatalui: ");
            initialaTataluiNeverificata = input.nextLine();
        }
        this.initialaTatalui = initialaTataluiNeverificata.toUpperCase();

        System.out.print("CNP-ul: ");
        String cnpNeverificat = input.nextLine();
        // repetarea cererii de introducere a CNP-ului de la user pana acesta introduce unul valid
        while (!verificareCnp(cnpNeverificat)) {
            System.out.println("INVALID!");
            System.out.print("Reintroduceti CNP-ul: ");
            cnpNeverificat = input.nextLine();
        }
        this.cnp = cnpNeverificat;
    }

    public Persoana(String nume, String prenume, String initialaTatalui, String cnp) {
        Scanner input = new Scanner(System.in);

        this.nume = nume;
        this.prenume = prenume;
        this.initialaTatalui = initialaTatalui;
        while (!verificareCnp(cnp)) {
            System.out.println("INVALID!");
            System.out.print("Reintroduceti CNP-ul: ");
            cnp = input.nextLine();
        }
        this.cnp = cnp;
    }

    public Persoana (String contor) {
        nume = prenume = initialaTatalui = cnp = nrTelefon = "";
    }

    private boolean verificareInitialaTatalui(String initialaTataluiNeverificata) {
        // trebuie sa fie un singur caracter
        String pattern = "^[A-Za-z]$";
        return initialaTataluiNeverificata.matches(pattern);
    }

    private boolean verificareCnp(String cnpNeverificat) {
        // primul caracter nu poate sa fie 0, iar restul 12 pot sa fie orice cifra
        // lungimea e de 13 caractere (cifre)
        String pattern = "^[1-9][0-9]{12}$";
        return cnpNeverificat.matches(pattern);
    }

    // getters
    public String getNume() {
        return nume;
    }
    public String getPrenume() {
        return prenume;
    }
    public String getInitialaTatalui() { return initialaTatalui;}
    public String getCnp() {
        return cnp;
    }
    public String getNrTelefon() {
        return nrTelefon;
    }

    public String numeComplet() {
        return nume + " " + initialaTatalui + " " + prenume;
    }
}
