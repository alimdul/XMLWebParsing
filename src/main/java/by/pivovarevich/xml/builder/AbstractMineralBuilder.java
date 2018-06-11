package by.pivovarevich.xml.builder;

import by.pivovarevich.xml.entity.AbstractMineral;
import by.pivovarevich.xml.exception.ParserException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMineralBuilder {

    protected List<AbstractMineral> mineralList;

    public AbstractMineralBuilder() {
        mineralList = new ArrayList<>();
    }

    public List<AbstractMineral> getMineralList() {
        return mineralList;
    }

    public abstract void buildMineralList(String fileName) throws ParserException;
}
