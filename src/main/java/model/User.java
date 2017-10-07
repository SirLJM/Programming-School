package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sql.DBManager;

public class User {

    private long id;
    private String username;
    private String email;
    private String password;
    private String salt;
    private int person_group_id;

    public User() {}

    public User(String username, String email, String password, int person_group_id) {
        this.id = 0;
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setPersonGroupId(person_group_id);
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
    public String getPassword() {
        return password;
    }
    public User setPassword(String password) {

        return this;
    }
    public long getId() {
        return id;
    }
    public User setPersonGroupId(int id) {
        this.person_group_id = id;
        return this;
    }
    public int getPersonGroupId() {
        return this.person_group_id;
    }
    @Override
    public String toString() {
        return "id: "+this.id+" username: "+this.username+" email:"+this.email+" password:" + this.password;
    }

    public void saveToDB() {
        if(this.id==0) {
            try {
                String generatedColumns[] = { "ID" };
                PreparedStatement stmt = DBManager.getPreparedStatement("INSERT INTO users(username,email,password,salt,person_group_id) VALUES (?,?,?,?,?)",generatedColumns);
                stmt.setString(1, this.username);
                stmt.setString(2, this.email);
                stmt.setString(3, this.password);
                stmt.setString(4, this.salt);
                stmt.setInt(5, this.person_group_id);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }else {
            try {
                PreparedStatement stmt = DBManager.getPreparedStatement("UPDATE users SET username=?, email=?, person_group_id=?, password=?, salt=? WHERE id=?");
                stmt.setString(1, this.username);
                stmt.setString(2, this.email);
                stmt.setInt(3, this.person_group_id);
                stmt.setString(4, this.password);
                stmt.setString(5, this.salt);
                stmt.setLong(6, this.id);
//				System.out.println(stmt);
                stmt.executeUpdate();
            }catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void delete() {
        String sql = "DELETE ";

    }

}