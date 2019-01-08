package cs445.a4;

/**
 * A type of runtime exception thrown when the system is found to be empty
 */
public class SystemEmptyException extends RuntimeException {
    SystemEmptyException(String msg) {
        super(msg);
    }
}

