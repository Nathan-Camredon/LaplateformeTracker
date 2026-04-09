package com.tracker.model;

import com.tracker.config.DatabaseConfig;
import java.sql.*;

public class StudentRequest {

    public void CreateStudent(String firstName, String lastName, int age, float average) {
        String sql = "INSERT INTO \"Student\" (first_name, last_name, age, average) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setFloat(4, average);

            pstmt.executeUpdate();
            System.out.println("✅ Étudiant ajouté avec succès : " + firstName + " " + lastName);

        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion détaillée :");
            e.printStackTrace();
        }
    }

    public void DeleteStudent(int id) {
        String sql = "DELETE FROM \"Student\" WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println(" Étudiant avec l'ID " + id + " supprimé.");
            } else {
                System.out.println("Aucun étudiant trouvé avec l'ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression : " + e.getMessage());
        }
    }

    public void Update(int id, String firstName, String lastName, int age, float average) {
        String sql = "UPDATE \"Student\" SET first_name = ?, last_name = ?, age = ?, average = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setFloat(4, average);
            pstmt.setInt(5, id);

            pstmt.executeUpdate();
            System.out.println("✅ Étudiant mis à jour !");

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    public void FindAll() {
        String sql = "SELECT * FROM \"Student\"";

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Liste des Étudiants ---");
            while (rs.next()) {
                System.out.println(String.format("ID: %d | %s %s | Age: %d | Moyenne: %.2f",
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getFloat("average")));
            }
            System.out.println("---------------------------\n");

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des étudiants : " + e.getMessage());
        }
    }
}
