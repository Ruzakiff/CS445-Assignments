package cs445.a4;

/**
 * A type of runtime exception thrown when song already has a rating given by user
 */
public class AlreadyHasRatingException extends RuntimeException {
   	AlreadyHasRatingException(String msg) {
        super(msg);
    }
}

