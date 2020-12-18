package mytunes.bll.exeption;
/**
 * custom exception thrown into higher layer
 * if exception in BLL package occurs its gracefully
 * handled and the custom exception is sent
 * @author kuba
 */
public class BLLexception extends Exception {

    public BLLexception(String message) {
        super(message);
    }

    public BLLexception(String message, Exception ex) {
        super(message, ex);
    }
}
