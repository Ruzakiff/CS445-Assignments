package cs445.a4;

/**
 * A type of runtime exception thrown when the system is found to be full
 */
public class SystemFullException extends RuntimeException {
    SystemFullException(String msg) {
        super(msg);
    }
}

