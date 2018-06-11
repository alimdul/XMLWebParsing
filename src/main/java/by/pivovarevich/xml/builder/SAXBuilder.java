package by.pivovarevich.xml.builder;

import by.pivovarevich.xml.entity.AbstractMineral;
import by.pivovarevich.xml.entity.PreciousStone;
import by.pivovarevich.xml.entity.SemipreciousStone;
import by.pivovarevich.xml.exception.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAXBuilder extends AbstractMineralBuilder {

    private Handler handler;

    public SAXBuilder() {
        handler = new Handler();
    }

    private class Handler extends DefaultHandler{

        private final String PRECIOUS_STONE = MineralTags.PRECIOUS_STONE.toString().toLowerCase().replace('_', '-');
        private final String SEMIPRECIOUS_STONE = MineralTags.SEMIPRECIOUS_STONE.toString().toLowerCase().replace('_', '-');
        private final String MINERALS_ID = MineralTags.MINERALS_ID.toString().toLowerCase().replace('_', '-');
        private final String NAME = MineralTags.NAME.toString().toLowerCase();
        private final String ORIGIN = MineralTags.ORIGIN.toString().toLowerCase();
        private final String COLOR = MineralTags.COLOR.toString().toLowerCase();
        private final String TRANSPARENCE = MineralTags.TRANSPARENCE.toString().toLowerCase();
        private final String VALUE = MineralTags.VALUE.toString().toLowerCase();
        private final String EDGES_NUMBER = MineralTags.EDGES_NUMBER.toString().toLowerCase().replace('_', '-');

        private boolean colorFlag = false;
        private boolean transparenceFlag = false;
        private boolean valueFlag = false;
        private boolean edgesNumberFlag = false;

        private AbstractMineral mineral;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {

            if(qName.equals(PRECIOUS_STONE)) {
                mineral = new PreciousStone();
            } else {
                if (qName.equals(SEMIPRECIOUS_STONE)) {
                    mineral = new SemipreciousStone();
                } else {
                    if (qName.equals(COLOR)) {
                        colorFlag = true;
                    } else {
                        if (qName.equals(TRANSPARENCE)) {
                            transparenceFlag = true;
                        } else {
                            if (qName.equals(VALUE)) {
                                valueFlag = true;
                            } else {
                                if (qName.equals(EDGES_NUMBER)) {
                                    edgesNumberFlag = true;
                                }
                            }
                        }
                    }
                }
            }
            for(int i=0; i<attributes.getLength(); i++) {
                attributesCharacters(attributes.getLocalName(i), attributes.getValue(i));
            }
        }

        public void attributesCharacters(String attribute, String value) {

            if(attribute.equals(MINERALS_ID)) {
                mineral.setId(value);
            } else {
                if (attribute.equals(NAME)) {
                    mineral.setName(value);
                } else {
                    if (attribute.equals(ORIGIN)) {
                        mineral.setOrigin(value);
                    }
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {

            if(colorFlag) {
                mineral.setColor(new String(ch, start, length));
                colorFlag = false;
            } else {
                if (transparenceFlag) {
                    mineral.setTransparence(Boolean.parseBoolean(new String(ch, start, length).trim()));
                    transparenceFlag = false;
                } else {
                    if (valueFlag) {
                        mineral.setValue(Double.parseDouble(new String(ch, start, length).trim()));
                        valueFlag = false;
                    } else {
                        if (edgesNumberFlag) {
                            mineral.setEdgesNumber(Integer.parseInt(new String(ch, start, length).trim()));
                            edgesNumberFlag = false;
                        }
                    }
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {

            if(qName.equals(PRECIOUS_STONE) || qName.equals(SEMIPRECIOUS_STONE)) {
                mineralList.add(mineral);
            }
        }
    }

    @Override
    public void buildMineralList(String fileName) throws ParserException {

        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(getClass().getClassLoader().getResource(fileName).getFile()), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ParserException("Unexpected fail in SAXBuilder", e);
        }
    }
}
