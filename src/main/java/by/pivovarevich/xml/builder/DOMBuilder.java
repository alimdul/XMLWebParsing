package by.pivovarevich.xml.builder;

import by.pivovarevich.xml.entity.AbstractMineral;
import by.pivovarevich.xml.entity.PreciousStone;
import by.pivovarevich.xml.entity.SemipreciousStone;
import by.pivovarevich.xml.exception.ParserException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DOMBuilder extends AbstractMineralBuilder {

    private final String PRECIOUS_STONE = MineralTags.PRECIOUS_STONE.toString().toLowerCase().replace('_', '-');
    private final String SEMIPRECIOUS_STONE = MineralTags.SEMIPRECIOUS_STONE.toString().toLowerCase().replace('_', '-');
    private final String MINERALS_ID = MineralTags.MINERALS_ID.toString().toLowerCase().replace('_', '-');
    private final String NAME = MineralTags.NAME.toString().toLowerCase();
    private final String ORIGIN = MineralTags.ORIGIN.toString().toLowerCase();
    private final String COLOR = MineralTags.COLOR.toString().toLowerCase();
    private final String TRANSPARENCE = MineralTags.TRANSPARENCE.toString().toLowerCase();
    private final String VALUE = MineralTags.VALUE.toString().toLowerCase();
    private final String EDGES_NUMBER = MineralTags.EDGES_NUMBER.toString().toLowerCase().replace('_', '-');

    @Override
    public void buildMineralList(String fileName) throws ParserException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(getClass().getClassLoader().getResource(fileName).getFile()));
            document.getDocumentElement().normalize();

            NodeList preciousStoneList = document.getElementsByTagName(PRECIOUS_STONE);
            for(int i=0; i<preciousStoneList.getLength(); i++) {
                AbstractMineral preciousStone = new PreciousStone();
                visitChildNodes(preciousStoneList, i, preciousStone);
            }
            NodeList semipreciousStoneList = document.getElementsByTagName(SEMIPRECIOUS_STONE);
            for(int i=0; i<semipreciousStoneList.getLength(); i++) {
                AbstractMineral semipreciousStone = new SemipreciousStone();
                visitChildNodes(semipreciousStoneList, i, semipreciousStone);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ParserException("Unexpected fail in DOMBuilder", e);
        }
    }

    private void visitChildNodes(NodeList stoneList, int i, AbstractMineral mineral) {
        Node node = stoneList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element stoneElement = (Element) node;

            mineral.setColor(stoneElement.getElementsByTagName(COLOR).item(0).getTextContent());
            mineral.setTransparence(Boolean.parseBoolean(stoneElement.getElementsByTagName(TRANSPARENCE).item(0).getTextContent()));
            mineral.setValue(Double.parseDouble(stoneElement.getElementsByTagName(VALUE).item(0).getTextContent()));
            mineral.setEdgesNumber(Integer.parseInt(stoneElement.getElementsByTagName(EDGES_NUMBER).item(0).getTextContent()));

            if (node.hasAttributes()) {
                NamedNodeMap nodeMap = node.getAttributes();
                for (int j = 0; j < nodeMap.getLength(); j++) {
                    Node currentNode = nodeMap.item(j);
                    if(currentNode.getNodeName().equals(MINERALS_ID)) {
                        mineral.setId(currentNode.getNodeValue());
                    } else {
                        if (currentNode.getNodeName().equals(NAME)) {
                            mineral.setName(currentNode.getNodeValue());
                        } else {
                            if (currentNode.getNodeName().equals(ORIGIN)) {
                                mineral.setOrigin(currentNode.getNodeValue());
                            }
                        }
                    }
                }
            }
        }
        mineralList.add(mineral);
    }
}
