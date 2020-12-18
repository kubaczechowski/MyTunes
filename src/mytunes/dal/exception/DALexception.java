package mytunes.dal.exception;

/**
 * custom exception thrown into higher layer
 * if exception in DAO package occurs its gracefully
 * handled and the custom exception is sent
 * @author kuba
 */
public class DALexception extends Exception{

    public DALexception() {
        super();
    }

    public DALexception(String messege) {
        super(messege);
    }

    public DALexception(String messege, Exception ex) {
        super(messege, ex);
    }
}
