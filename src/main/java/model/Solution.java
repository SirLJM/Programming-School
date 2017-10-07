package model;

import java.util.Date;

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

}
