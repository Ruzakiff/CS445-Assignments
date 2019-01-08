package cs445.a4;

/**
 * A type of runtime exception thrown when the station is found to be empty
 */
public class StationEmptyException extends RuntimeException {
    StationEmptyException(String msg) {
        super(msg);
    }
}

