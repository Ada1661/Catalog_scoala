package Catalog;

import Administrare.Audit;
import Administrare.SingletonBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CatalogPrimara extends Catalog{
    // nr de absente pentru fiecare
    Map<MateriePersoana, Integer> absente = new HashMap<>();
    // notele pentru fiecare
    Map<MateriePersoana, List<String>> note = new HashMap<>();
    List<String> Calificative = new ArrayList<>();

    private SingletonBD serviciuBD;
    private Audit audit;

    CatalogPrimara(int val) {
        super(val);
    }

    CatalogPrimara() {
        super();


        audit = new Audit();
        serviciuBD = SingletonBD.getInstance();
        serviciuBD.setConnection(connection);
        String createTableQuery = "CREATE TABLE IF NOT EXISTS catalog_primara (id INT AUTO_INCREMENT PRIMARY KEY, materie VARCHAR(255), persoana VARCHAR(255), nota VARCHAR(255), absenta INT)";
        serviciuBD.execUpdate(createTableQuery);

        for (MateriePersoana materiePersoana : CatalogulDeBaza) {
            absente.put(materiePersoana, 0);
            note.put(materiePersoana, Calificative);
        }
        SingletonBD bazaDate = SingletonBD.getInstance();
        String insertQuery = "INSERT INTO catalog_primara (materie, persoana) VALUES (?, ?)";

        try {
            PreparedStatement insertStatement = serviciuBD.getConnection().prepareStatement(insertQuery);
            for (MateriePersoana item : CatalogulDeBaza) {
                insertStatement.setString(1, item.getMaterie());
                insertStatement.setString(2, item.getNumeComplet());
                insertStatement.executeUpdate();
            }
            audit.log("CREATE", "catalog_primara", "materie, persoana");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean verificarePersoana(String nume, String prenume, String initialaTatalui) {
        for (Persoana persoana : Persoane) {
            if (persoana.getNume().equals(nume) && persoana.getPrenume().equals(prenume) && persoana.getInitialaTatalui().equals(initialaTatalui)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificareMaterie(String materie) {
        for (String Materie : Materii) {
            if (Materie.equals(materie)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificareCalificativ(String calificativ) {
        if (calificativ.equals("FB") || calificativ.equals("B") || calificativ.equals("S") || calificativ.equals("I")) {
            return true;
        }
        return false;
    }

    public void adaugaAbsenta() {
        String numeAbsent;
        String prenumeAbsent;
        String initialaTataluiAbsent;
        String materieAbsent;
        int nrAbsente = 0;

        Scanner input = new Scanner(System.in);
        System.out.print("Numele elevului: ");
        numeAbsent = input.nextLine();
        System.out.print("Prenumele elevului: ");
        prenumeAbsent = input.nextLine();
        System.out.print("Initiala tatalui: ");
        initialaTataluiAbsent = input.nextLine();
        System.out.print("Denumire materie: ");
        materieAbsent = input.nextLine();

        SingletonBD bazaDate = SingletonBD.getInstance();
        if (bazaDate == null) {
            System.out.println("Database connection is null. Make sure the connection is established.");
            return;
        }

        String selectQuery = "SELECT absenta AS num_abs FROM catalog_primara WHERE materie = ? AND persoana = ?";
        try {
            PreparedStatement selectStatement = bazaDate.getConnection().prepareStatement(selectQuery);
            selectStatement.setString(1, materieAbsent);
            selectStatement.setString(2, numeAbsent + " " + initialaTataluiAbsent + " " + prenumeAbsent);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                nrAbsente = resultSet.getInt("num_abs");
                nrAbsente += 1;
            }

            resultSet.close();
            selectStatement.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            String updateQuery = "UPDATE catalog_primara SET absenta = ? WHERE materie = ? AND persoana = ?";
            PreparedStatement updateStatement = bazaDate.getConnection().prepareStatement(updateQuery);
            updateStatement.setInt(1, nrAbsente);
            updateStatement.setString(2, materieAbsent);
            updateStatement.setString(3, numeAbsent + " " + initialaTataluiAbsent + " " + prenumeAbsent);
            updateStatement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

    }


    public void motiveazaAbsenta() {
        String numeAbsent;
        String prenumeAbsent;
        String initialaTataluiAbsent;
        String materieAbsent;
        int nrAbsente = 0;

        Scanner input = new Scanner(System.in);
        System.out.print("Numele elevului: ");
        numeAbsent = input.nextLine();
        System.out.print("Prenumele elevului: ");
        prenumeAbsent = input.nextLine();
        System.out.print("Initiala tatalui: ");
        initialaTataluiAbsent = input.nextLine();
        System.out.print("Denumire materie: ");
        materieAbsent = input.nextLine();

        SingletonBD bazaDate = SingletonBD.getInstance();
        if (bazaDate == null) {
            System.out.println("Database connection is null. Make sure the connection is established.");
            return;
        }

        String selectQuery = "SELECT absenta AS num_abs FROM catalog_primara WHERE materie = ? AND persoana = ?";
        try {
            PreparedStatement selectStatement = bazaDate.getConnection().prepareStatement(selectQuery);
            selectStatement.setString(1, materieAbsent);
            selectStatement.setString(2, numeAbsent + " " + initialaTataluiAbsent + " " + prenumeAbsent);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                nrAbsente = resultSet.getInt("num_abs");
                nrAbsente -= 1;
            }

            resultSet.close();
            selectStatement.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            String updateQuery = "UPDATE catalog_primara SET absenta = ? WHERE materie = ? AND persoana = ?";
            PreparedStatement updateStatement = bazaDate.getConnection().prepareStatement(updateQuery);
            updateStatement.setInt(1, nrAbsente);
            updateStatement.setString(2, materieAbsent);
            updateStatement.setString(3, numeAbsent + " " + initialaTataluiAbsent + " " + prenumeAbsent);
            updateStatement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void adaugaCalificativ() {
        String nume;
        String prenume;
        String initialaTatalui;
        String materie;
        String nota;

        Scanner input = new Scanner(System.in);
        System.out.print("Numele elevului: ");
        nume = input.nextLine();
        System.out.print("Prenumele elevului: ");
        prenume = input.nextLine();
        System.out.print("Initiala tatalui: ");
        initialaTatalui = input.nextLine();
        System.out.print("Denumire materie: ");
        materie = input.nextLine();
        System.out.print("Nota obtinuta: ");
        nota = input.nextLine();

        String nr = nota;
        while (!verificareCalificativ(nr)) {
            System.out.println("INVALID!");
            System.out.print("Reintroduceti nota: ");
            nr = input.nextLine();
            nota = nr;
        }

        SingletonBD bazaDate = SingletonBD.getInstance();
        String updateQuery = "UPDATE catalog_primara SET nota = ? WHERE materie = ? AND persoana = ?";
        try {
            PreparedStatement updateStatement = bazaDate.getConnection().prepareStatement(updateQuery);
            updateStatement.setString(1, nota);
            updateStatement.setString(2, materie);
            updateStatement.setString(3, nume + " " + initialaTatalui + " " + prenume);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                if (audit == null) {
                    audit = new Audit();
                }
                audit.log("UPDATE", "catalog_primara", "nota");
                System.out.println("Nota actualizata cu succes.");
            }
            else {
                System.out.println("Nu s-a gasit o intrare corespunzatoare in baza de date.");
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void afisareCatalogData() {
        String selectQuery = "SELECT * FROM catalog_primara";
        if (serviciuBD == null) {
            serviciuBD = serviciuBD.getInstance();
        }

        if (audit == null) {
            audit = new Audit();
        }

        ResultSet resultSet = serviciuBD.execQuery(selectQuery);
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String materie = resultSet.getString("materie");
                String persoana = resultSet.getString("persoana");

                System.out.println("ID: " + id);
                System.out.println("Materie: " + materie);
                System.out.println("Persoana: " + persoana);
                System.out.println("--------------------");
            }
            audit.log("READ", "catalog_primara", "materie, persoana");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void stergereMateriePersoana(int id) {
        SingletonBD bazaDate = SingletonBD.getInstance();
        if (audit == null) {
            audit = new Audit();
        }
        String deleteQuery = "DELETE FROM catalog_primara WHERE id = ?";
        try {
            PreparedStatement deleteStatement = bazaDate.getConnection().prepareStatement(deleteQuery);
            deleteStatement.setInt(1, id);
            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("MateriePersoana record deleted succesfully.");
            }
            else {
                System.out.println("No matching records found to delete.");
            }
            audit.log("DELETE", "catalog_primara", "materie, persoana");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CatalogPrimara catalogPrimara = new CatalogPrimara();
        catalogPrimara.adaugaCalificativ();
        catalogPrimara.adaugaAbsenta();
        catalogPrimara.motiveazaAbsenta();
    }
}
