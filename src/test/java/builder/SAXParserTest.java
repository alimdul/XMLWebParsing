package builder;

import by.pivovarevich.xml.builder.AbstractMineralBuilder;
import by.pivovarevich.xml.builder.MineralBuilderFactory;
import by.pivovarevich.xml.exception.IncorrectInputParametersException;
import by.pivovarevich.xml.exception.ParserException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SAXParserTest {

    @Test
    public void parse() throws IncorrectInputParametersException, ParserException {

        MineralBuilderFactory factory = new MineralBuilderFactory();
        AbstractMineralBuilder builder = factory.createMineralBuilder("DOM");
        builder.buildMineralList("data/minerals.xml");

        Assert.assertEquals(builder.getMineralList().size(), 16);
    }
}
