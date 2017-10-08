package model;

import sql.DBManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {

    private int id;
    private String name;

    public Group() {}

    public Group(String name) {
        this.id = 0;
        setName(name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void saveToDB() {
        if (this.id==0) {
            try {
                PreparedStatement stmt = DBManager.getPreparedStatement("INSERT INTO user_group(name) VALUES ?");
                stmt.setString(1, this.name);
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
                PreparedStatement stmt = DBManager.getPreparedStatement("UPDATE user_group SET name=? WHERE id=?");
                stmt.setString(1, this.name);
                stmt.setInt(2, this.id);
                stmt.executeUpdate();
            }catch (NullPointerException | SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void delete() {
        String sql = "DELETE FROM user_group WHERE id = ?";
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

    public static ArrayList<Group> getGroupsFromStmt(PreparedStatement stmt) {
        ArrayList<Group> groups = new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Group loadedGroup = new Group();
                loadedGroup.id = resultSet.getInt("id");
                loadedGroup.name = resultSet.getString("name");
                groups.add(loadedGroup);
            }
            return groups;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Group> loadAll() {
        String sql = "SELECT * FROM user_group";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        return getGroupsFromStmt(stmt);
    }

    public static ArrayList<Group> loadById(int id) {
        String sql = "SELECT * FROM user_group WHERE id = ?";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        try {
            stmt.setInt(1, id);
            return getGroupsFromStmt(stmt);
        } catch (NullPointerException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}