package Catalog;

import java.util.Scanner;

public class Elev extends Persoana {
    protected String nrTelefon;

    public Elev(String contor) {
        nume = prenume = initialaTatalui = cnp = nrTelefon = "";
    }

    public Elev(int variable) {
        super(1);

        Scanner input = new Scanner(System.in);

        System.out.println("Completati datele unui parinte/tutore legal!");
        System.out.print("Numarul de telefon: ");
        String nrTelefonNeverificat = input.nextLine();
        // repetarea cererii de introducere a numarului de telefon de la user pana acesta introduce unul valid
        while (!verificareNrTelefon(nrTelefonNeverificat)) {
            System.out.println("INVALID!");
            System.out.print("Reintroduceti numarul de telefon: ");
            nrTelefonNeverificat = input.nextLine();
        }
        this.nrTelefon = nrTelefonNeverificat;
    }

    public Elev(String nume, String prenume, String initialaTatalui, String cnp, String nrTelefon) {
        super(nume, prenume, initialaTatalui, cnp);
        Scanner input = new Scanner(System.in);
        while (!verificareNrTelefon(nrTelefon)) {
            System.out.println("INVALID!");
            System.out.print("Reintroduceti numarul de telefon: ");
            nrTelefon = input.nextLine();
        }
        this.nrTelefon = nrTelefon;
    }

    private boolean verificareNrTelefon(String nrTelefonNeverificat) {
        // primele 2 cifre trebuie sa fie 0 si 7 si restul 8 pot sa fie orice cifra
        // lungimea e de 10 caractere (cifre)
        String pattern = "^07[0-9]{8}$";
        return nrTelefonNeverificat.matches(pattern);
    }

    // getters
    public String getNrTelefon() {
        return nrTelefon;
    }

    // setters
    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }
}
