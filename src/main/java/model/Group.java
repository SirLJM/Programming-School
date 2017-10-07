package model;

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


}