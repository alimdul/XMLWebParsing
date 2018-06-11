package by.pivovarevich.xml.builder;

import by.pivovarevich.xml.exception.IncorrectInputParametersException;

public class MineralBuilderFactory {

    public AbstractMineralBuilder createMineralBuilder(String type) throws IncorrectInputParametersException {

        BuilderTypes builderTypes = BuilderTypes.valueOf(type);

        switch (builderTypes){
            case DOM:
                return new DOMBuilder();
            case SAX:
                    return new SAXBuilder();
            case STAX:
                return new StAXBuilder();
            default:
                throw new IncorrectInputParametersException(type + " is incorrect input parameter!");
        }
    }
}
