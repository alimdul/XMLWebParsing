package by.pivovarevich.xml.entity;

import java.util.Objects;

public class SemipreciousStone extends AbstractMineral {

    private static final String DEFAULT_ORIGIN = "Unknown";

    private String name;
    private String origin;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOrigin() {
        if(origin == null || origin.isEmpty()) {
            return DEFAULT_ORIGIN;
        } else {
            return origin;
        }
    }

    @Override
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getOrigin(), getId(), getColor(), isTransparence(), getValue(), getEdgesNumber());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PreciousStone)) return false;
        PreciousStone preciousStone = (PreciousStone) obj;

        return  Objects.equals(getName(), preciousStone.getName()) &&
                Objects.equals(getOrigin(), preciousStone.getOrigin()) &&
                Objects.equals(getId(), preciousStone.getId()) &&
                Objects.equals(getColor(), preciousStone.getColor()) &&
                isTransparence() == preciousStone.isTransparence() &&
                getValue() == preciousStone.getValue() &&
                getEdgesNumber() == preciousStone.getEdgesNumber();
    }

    @Override
    public String toString() {
        return "SemipreciousStone:  " +
                " name=" + getName() +
                " origin=" + getOrigin() +
                " id=" + getId() +
                " color=" + getColor() +
                " transparence=" + isTransparence() +
                " value=" + getValue() +
                " edgesNumber=" + getEdgesNumber();
    }
}
