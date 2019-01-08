package cs445.a4;

/**
 * A type of runtime exception thrown when station is found to be full
 */
public class StationFullException extends RuntimeException {
    StationFullException(String msg) {
        super(msg);
    }
}

