package exceptions;

public class ProfileDelete extends RuntimeException {
    public ProfileDelete(String message) {
        super(message);
    }
}
