package by.pivovarevich.xml.exception;

public class IncorrectInputParametersException extends Exception {

    public IncorrectInputParametersException(String message) {
        super(message);
    }

    public IncorrectInputParametersException(String message, Throwable cause) {
        super(message, cause);
    }
}
