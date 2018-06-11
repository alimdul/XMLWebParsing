package by.pivovarevich.xml.builder;

import by.pivovarevich.xml.entity.AbstractMineral;
import by.pivovarevich.xml.entity.PreciousStone;
import by.pivovarevich.xml.entity.SemipreciousStone;
import by.pivovarevich.xml.exception.ParserException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StAXBuilder extends AbstractMineralBuilder {

    private final String PRECIOUS_STONE = MineralTags.PRECIOUS_STONE.toString().toLowerCase().replace('_', '-');
    private final String SEMIPRECIOUS_STONE = MineralTags.SEMIPRECIOUS_STONE.toString().toLowerCase().replace('_', '-');
    private final String MINERALS_ID = MineralTags.MINERALS_ID.toString().toLowerCase().replace('_', '-');
    private final String NAME = MineralTags.NAME.toString().toLowerCase();
    private final String ORIGIN = MineralTags.ORIGIN.toString().toLowerCase();
    private final String COLOR = MineralTags.COLOR.toString().toLowerCase();
    private final String TRANSPARENCE = MineralTags.TRANSPARENCE.toString().toLowerCase();
    private final String VALUE = MineralTags.VALUE.toString().toLowerCase();
    private final String EDGES_NUMBER = MineralTags.EDGES_NUMBER.toString().toLowerCase().replace('_', '-');

    private static final String DEFAULT_ORIGIN = "Unknown";

    @Override
    public void buildMineralList(String fileName) throws ParserException {

        AbstractMineral mineral = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(
                    new FileInputStream(getClass().getClassLoader().getResource(fileName).getFile()));

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();

                    if (startElement.getName().getLocalPart().equals(PRECIOUS_STONE)) {
                        mineral = new PreciousStone();
                        attributeParsing(mineral, startElement);
                    }
                    if (startElement.getName().getLocalPart().equals(SEMIPRECIOUS_STONE)) {
                        mineral = new SemipreciousStone();
                        attributeParsing(mineral, startElement);
                    }

                    else {
                        if (startElement.getName().getLocalPart().equals(COLOR)) {
                            xmlEvent = xmlEventReader.nextEvent();
                            mineral.setColor(xmlEvent.asCharacters().getData());
                        } else {
                            if (startElement.getName().getLocalPart().equals(TRANSPARENCE)) {
                                xmlEvent = xmlEventReader.nextEvent();
                                mineral.setTransparence(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                            } else {
                                if (startElement.getName().getLocalPart().equals(VALUE)) {
                                    xmlEvent = xmlEventReader.nextEvent();
                                    mineral.setValue(Double.parseDouble(xmlEvent.asCharacters().getData()));
                                } else {
                                    if (startElement.getName().getLocalPart().equals(EDGES_NUMBER)) {
                                        xmlEvent = xmlEventReader.nextEvent();
                                        mineral.setEdgesNumber(Integer.parseInt(xmlEvent.asCharacters().getData()));
                                    }
                                }
                            }
                        }
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals(PRECIOUS_STONE) ||
                            endElement.getName().getLocalPart().equals(SEMIPRECIOUS_STONE)) {
                        mineralList.add(mineral);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            throw new ParserException("Unexpected fail in StAXBuilder", e);
        }
    }

    private void attributeParsing(AbstractMineral mineral, StartElement startElement) {
        String mineralsId = startElement.getAttributeByName(new QName(MINERALS_ID)).getValue();
        if (mineralsId != null) {
            mineral.setId(mineralsId);
        }
        String name = startElement.getAttributeByName(new QName(NAME)).getValue();
        if (name != null) {
            mineral.setName(name);
        }
        if(startElement.getAttributeByName(new QName(ORIGIN)) == null) {
            mineral.setOrigin(DEFAULT_ORIGIN);
        } else {
            String origin = startElement.getAttributeByName(new QName(ORIGIN)).getValue();
            if (origin != null) {
                mineral.setOrigin(origin);
            }
        }
    }
}
