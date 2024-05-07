package by.bsuir.realEstate.utils;


public class ObjectIsPresentException extends Exception{
    public ObjectIsPresentException(String errorMessage) {
        super(errorMessage);
    }

    public ObjectIsPresentException() {
    }
}
