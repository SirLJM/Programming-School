package model;

import sql.DBManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private int exercise_id;
    private long users_id;

    public Solution() {}

    public Solution(Date created, Date updated, String description, int exercise_id, long users_id) {
        this.id = 0;
        setCreated(created);
        setUpdated(updated);
        setDescription(description);
        setExercise_id(exercise_id);
        setUsers_id(users_id);
    }

    public int getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public long getUsers_id() {
        return users_id;
    }

    public void setUsers_id(long users_id) {
        this.users_id = users_id;
    }

    public void saveToDB() {
        if (this.id==0) {
            try {
                PreparedStatement stmt = DBManager.getPreparedStatement("INSERT INTO solution(created, updated, description, exercise_id, users_id) VALUES (?,?,?,?,?)");
                stmt.setDate(1, this.created);
                stmt.setDate(2, this.updated);
                stmt.setString(3, this.description);
                stmt.setInt(4, this.exercise_id);
                stmt.setLong(5, this.users_id);
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
                PreparedStatement stmt = DBManager.getPreparedStatement("UPDATE solution SET created = ?, updated = ?, description = ?, exercise_id = ?, users_id = ? WHERE id = ?");
                stmt.setDate(1, this.created);
                stmt.setDate(2, this.updated);
                stmt.setString(3, this.description);
                stmt.setInt(4, this.exercise_id);
                stmt.setLong(5, this.users_id);
                stmt.setInt(6, this.id);
                stmt.executeUpdate();
            }catch (NullPointerException | SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void delete() {
        String sql = "DELETE FROM solution WHERE id = ?";
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

    public static ArrayList<Solution> getSolutionsFromStmt(PreparedStatement stmt) {
        ArrayList<Solution> solutions = new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Solution loadedSolution = new Solution();
                loadedSolution.id = resultSet.getInt("id");
                loadedSolution.created = resultSet.getDate("created");
                loadedSolution.updated = resultSet.getDate("updated");
                loadedSolution.description = resultSet.getString("description");
                loadedSolution.exercise_id = resultSet.getInt("exercise_id");
                loadedSolution.users_id = resultSet.getLong("users_id");
                solutions.add(loadedSolution);
            }
            return solutions;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Solution> loadAll() {
        String sql = "SELECT * FROM solutions";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        return getSolutionsFromStmt(stmt);
    }

    public static ArrayList<Solution> loadById(int id) {
        String sql = "SELECT * FROM solutions WHERE id = ?";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        try {
            stmt.setInt(1, id);
            return getSolutionsFromStmt(stmt);
        } catch (NullPointerException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Solution> loadAllByExerciseId(int id) {
        String sql = "SELECT * FROM solution WHERE exercise.id = ?";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        try {
            stmt.setInt(1, id);
            return getSolutionsFromStmt(stmt);
        } catch (NullPointerException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
