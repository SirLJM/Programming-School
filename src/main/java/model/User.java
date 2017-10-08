package model;

import sql.DBManager;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public String setSalt() {
        byte [] _salt;
        SecureRandom secureRandom = new SecureRandom();
        _salt = secureRandom.generateSeed(10);
        this.salt = new String(_salt, StandardCharsets.UTF_8);
        return salt;
    }

    public String getSalt() { return salt; }

    public String getPassword() { return password; }

    public User setPassword(String password) {
//        setSalt();
        this.password = password;
        String hashpass = password + setSalt();



//        try {
//
//        } catch () {
//
//        }
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
        return "id: " + this.id + " username: " + this.username + " email:" + this.email + " password:" + this.password;
    }

    public void saveToDB() {
        if(this.id==0) {
            try {
//                String generatedColumns[] = { "ID" };
//                PreparedStatement stmt = DBManager.getPreparedStatement("INSERT INTO users(username, email, password, salt, person_group_id) VALUES (?,?,?,?,?)",generatedColumns);
                PreparedStatement stmt = DBManager.getPreparedStatement("INSERT INTO users(username, email, password, salt, person_group_id) VALUES (?,?,?,?,?)");
                stmt.setString(1, this.username);
                stmt.setString(2, this.email);
                stmt.setString(3, this.password);
                stmt.setString(4, this.salt);
                stmt.setInt(5, this.person_group_id);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getLong(1);
                }
            } catch (NullPointerException | SQLException e) {
                System.err.println(e.getMessage());
            }
        }else {
            try {
                PreparedStatement stmt = DBManager.getPreparedStatement("UPDATE users SET username = ?, email = ?, person_group_id = ?, password = ?, salt = ? WHERE id = ?");
                stmt.setString(1, this.username);
                stmt.setString(2, this.email);
                stmt.setInt(3, this.person_group_id);
                stmt.setString(4, this.password);
                stmt.setString(5, this.salt);
                stmt.setLong(6, this.id);
//				System.out.println(stmt);
                stmt.executeUpdate();
            } catch (NullPointerException | SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void delete() {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            if (this.id!=0) {
                PreparedStatement stmt = DBManager.getPreparedStatement(sql);
                stmt.setLong(1, this.id);
                stmt.executeUpdate();
                System.out.println("Deleted record: " + this.id);
                this.id = 0;
            }
        } catch (NullPointerException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static ArrayList<User> getUsersFromStmt(PreparedStatement stmt) {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                User loadedUser = new User();
                loadedUser.id = resultSet.getLong("id");
                loadedUser.username = resultSet.getString("username");
                loadedUser.email = resultSet.getString("email");
                loadedUser.password = resultSet.getString("password");
                loadedUser.salt = resultSet.getString("salt");
                loadedUser.person_group_id = resultSet.getInt("person_group_id");
                users.add(loadedUser);
            }
            return users;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<User> loadAll() {
        String sql = "SELECT * FROM users";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        return getUsersFromStmt(stmt);
    }

    public static ArrayList<User> loadById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        try {
            stmt.setLong(1, id);
            return getUsersFromStmt(stmt);
        } catch (NullPointerException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<User> loadAllByGroupId(int group_id) {
        String sql = "SELECT * FROM users JOIN user_group ON users.person_group_id = user_group.id WHERE user_group.id = ?";
        PreparedStatement stmt = DBManager.getPreparedStatement(sql);
        try {
            stmt.setInt(1, group_id);
            return getUsersFromStmt(stmt);
        } catch (NullPointerException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}