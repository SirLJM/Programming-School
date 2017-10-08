package model;

import sql.DBManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public Exercise() {}

    public Exercise(String title, String description) {
        this.id = 0;
        setTitle(title);
        setDescription(description);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void saveToDB() {
        if (this.id==0) {
            try {
                PreparedStatement stmt = DBManager.getPreparedStatement("INSERT INTO exercise(title, description) VALUES (?,?)");
                stmt.setString(1, this.title);
                stmt.setString(2, this.description);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } catch (NullPointerException | SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            try {
                PreparedStatement stmt = DBManager.getPreparedStatement("UPDATE exercise SET title = ?, description = ? WHERE id = ?");
                stmt.setString(1, this.title);
                stmt.setString(2, this.description);
                stmt.setInt(3, this.id);
                stmt.executeUpdate();
            }catch (NullPointerException | SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void delete() {
        String sql = "DELETE FROM exercise WHERE id = ?";
        try {
            if (this.id!=0) {
                PreparedStatement stmt = DBManager.getPreparedStatement(sql);
                stmt.setInt(1, this.id);
                stmt.executeUpdate();
                System.out.println("Deleted record: " + this.id);
                this.id = 0;
            }
        } catch (NullPointerException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static ArrayList<Exercise> getExerciseFromStmt(PreparedStatement stmt) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = resultSet.getInt("id");
                loadedExercise.title = resultSet.getString("title");
                loadedExercise.description = resultSet.getString("description");
                exercises.add(loadedExercise);
            }
            return exercises;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Exercise> loadAll() {
        String sql = "SELECT * FROM exercises";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        return getExerciseFromStmt(stmt);
    }

    public static ArrayList<Exercise> loadById(int id) {
        String sql = "SELECT * FROM exercise WHERE id = ?";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        try {
            stmt.setInt(1, id);
            return getExerciseFromStmt(stmt);
        } catch (NullPointerException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Exercise> loadAllByUserId (long id) {
        String sql = "SELECT * FROM exercise JOIN solution ON solution.exercise_id = exercise_id JOIN users ON solution.users_id = users.id WHERE users_id = ?";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        try {
            stmt.setLong(1, id);
            return getExerciseFromStmt(stmt);
        } catch (NullPointerException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}