package by.pivovarevich.xml.entity;

public abstract class AbstractMineral {

    private String id;
    private String color;
    private boolean transparence;
    private double value;
    private int edgesNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return  color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isTransparence() {
        return transparence;
    }

    public void setTransparence(boolean transparence) {
        this.transparence = transparence;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getEdgesNumber() {
        return edgesNumber;
    }

    public void setEdgesNumber(int edgesNumber) {
        this.edgesNumber = edgesNumber;
    }

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getOrigin();

    public abstract void setOrigin(String origin);
}
