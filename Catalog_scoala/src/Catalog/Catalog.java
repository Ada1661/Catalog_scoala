package Catalog;

import Administrare.Audit;
import Administrare.MyJDBC;
import Administrare.SingletonBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class Catalog {
    int nrMaterii;
    int nrElevi;
    // retinem materiile intr-o lista de stringuri
    List<String> Materii = new ArrayList<>();
    // retinem obiecte de tip Elev, persoane
    List<Elev> Persoane = new ArrayList<>();

    // retinem despre materii si persoane
    List<MateriePersoana> CatalogulDeBaza = new ArrayList<>();

    MyJDBC jdbc = MyJDBC.getInstance();
    Connection connection = jdbc.getConnection();
    private SingletonBD serviciuBD;
    private Audit audit;
    public Catalog(int val) {
        audit = new Audit();
        serviciuBD = serviciuBD.getInstance();
        serviciuBD.setConnection(connection);
        SingletonBD BazaDate = SingletonBD.getInstance();
    }

    public Catalog() {
        audit = new Audit();
        serviciuBD = serviciuBD.getInstance();
        serviciuBD.setConnection(connection);
        String createTableQuery = "CREATE TABLE IF NOT EXISTS catalog_data (id INT AUTO_INCREMENT PRIMARY KEY, materie VARCHAR(255), persoana VARCHAR(255))";
        serviciuBD.execUpdate(createTableQuery);

        Scanner input = new Scanner(System.in);
        System.out.print("Numar de materii: ");
        this.nrMaterii = input.nextInt();
        for (int i = 0; i < nrMaterii; i++) {
            System.out.print("Nume materie: ");
            String materie = input.next();
            Materii.add(materie);
        }
        System.out.print("Numarul de elevi: ");
        this.nrElevi = input.nextInt();
        for (int i = 0; i < nrElevi; i++) {
            System.out.println("Introduceti un elev: ");
            Elev elev = new Elev(1);
            Persoane.add(elev);
        }

        for (String Materie : Materii) {
            for (Elev persoana : Persoane) {
                MateriePersoana materiePersoana = new MateriePersoana(Materie, persoana);
                CatalogulDeBaza.add(materiePersoana);
            }
        }

        SingletonBD bazaDate = SingletonBD.getInstance();

        String insertQuery = "INSERT INTO catalog_data (materie, persoana) VALUES (?, ?)";
        try {
            PreparedStatement insertStatement = bazaDate.getConnection().prepareStatement(insertQuery);

            for (MateriePersoana item : CatalogulDeBaza) {
                insertStatement.setString(1, item.getMaterie());
                insertStatement.setString(2, item.getNumeComplet());
                insertStatement.executeUpdate();
            }
            audit.log("CREATE", "catalog_data", "materie, persoana");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void adaugaMateriePersoana() {
        SingletonBD bazaDate = SingletonBD.getInstance();

        String insertQuery = "INSERT INTO catalog_data (materie, persoana) VALUES (?, ?)";
        try {
            PreparedStatement insertStatement = bazaDate.getConnection().prepareStatement(insertQuery);

            for (MateriePersoana item : CatalogulDeBaza) {
                insertStatement.setString(1, item.getMaterie());
                insertStatement.setString(2, item.getNumeComplet());
                insertStatement.executeUpdate();
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateCatalog(String materie, String persoana) {
        String updateQuery = "UPDATE catalog_data SET materie = ? WHERE persoana = ?";
        try {
            PreparedStatement updateStatement = serviciuBD.getConnection().prepareStatement(updateQuery);
            updateStatement.setString(1, materie);
            updateStatement.setString(2, persoana);

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Catalog data updated successfully.");
            }
            else {
                System.out.println("0 rows changed.");
            }

            updateStatement.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        audit.log("UPDATE", "catalog_data", "materie, persoana");
    }

    public void afisareCatalogData() {
        String selectQuery = "SELECT * FROM catalog_data";
        ResultSet resultSet = serviciuBD.execQuery(selectQuery);

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String materie = resultSet.getString("materie");
                String persoana = resultSet.getString("persoana");

                System.out.println("ID: " + id);
                System.out.println("Materie: " + materie);
                System.out.println("Persoana: " + persoana);
                System.out.println("-----------------------");
            }
            audit.log("READ", "catalog_data", "materie, persoana");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void afisareCatalogDeBaza() {
        for (MateriePersoana item : CatalogulDeBaza) {
            System.out.println("Materie: " + item.getMaterie() + ", Persoana: " + item.getNumeComplet());
        }
    }

    private void stergereMateriePersoana(int id) {
        SingletonBD bazaDate = SingletonBD.getInstance();

        String deleteQuery = "DELETE FROM catalog_data WHERE id = ?";
        try {
            PreparedStatement deleteStatement = bazaDate.getConnection().prepareStatement(deleteQuery);
            deleteStatement.setInt(1, id);
            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("MateriePersoana record deleted successfully.");
            }
            else {
                System.out.println("No matching records found to delete.");
            }
            audit.log("DELETE", "catalog_data", "materie, persoana");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    // getter
    int getNrElevi() {
        return nrElevi;
    }

    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        catalog.afisareCatalogDeBaza();
    }
}
