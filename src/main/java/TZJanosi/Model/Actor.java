package TZJanosi.Model;

public class Actor {
    private long id;
    private String name;
    private int yob;

    public Actor(String name, int yob) {
        this.name = name;
        this.yob = yob;
    }

    public Actor(long id, String name, int yob) {
        this(name,yob);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYob() {
        return yob;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yob=" + yob +
                '}';
    }
}
