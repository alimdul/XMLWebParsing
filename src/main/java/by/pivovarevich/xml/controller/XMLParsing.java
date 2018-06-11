package by.pivovarevich.xml.controller;

import by.pivovarevich.xml.builder.AbstractMineralBuilder;
import by.pivovarevich.xml.builder.MineralBuilderFactory;
import by.pivovarevich.xml.exception.IncorrectInputParametersException;
import by.pivovarevich.xml.exception.ParserException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

@WebServlet("/XMLParsing")
public class XMLParsing extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAMETER_FOR_TYPE_OF_PARSING = "button";
    private static final String PARAMETER_FOR_PATH_TO_XML = "pathToXML";
    private static final String PARAMETER_FOR_PATH_TO_XSD = "pathToXSD";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(XMLValidation(request)) {
            String button = request.getParameter(PARAMETER_FOR_TYPE_OF_PARSING);

            MineralBuilderFactory factory = new MineralBuilderFactory();
            AbstractMineralBuilder builder = null;
            try {
                builder = factory.createMineralBuilder(button.toUpperCase());
                builder.buildMineralList(request.getParameter(PARAMETER_FOR_PATH_TO_XML));
            } catch (IncorrectInputParametersException | ParserException e) {
                LOGGER.catching(e);
            }
            request.setAttribute("mineralList", builder.getMineralList());
            request.getRequestDispatcher("/jsp/listOfMinerals.jsp").forward(request, response);
        }
        else {
            LOGGER.log(Level.ERROR, "XML is invalid");
        }
    }

    private boolean XMLValidation(HttpServletRequest request) {
        File XSDFile = new File(getClass().getClassLoader().getResource(request.getParameter(PARAMETER_FOR_PATH_TO_XSD)).getFile());
        File XMLFile = new File(getClass().getClassLoader().getResource(request.getParameter(PARAMETER_FOR_PATH_TO_XML)).getFile());
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(XSDFile)
                    .newValidator()
                    .validate(new StreamSource(XMLFile));
        } catch (SAXException | IOException e) {
            LOGGER.catching(e);
            return false;
        }
        return true;
    }
}
