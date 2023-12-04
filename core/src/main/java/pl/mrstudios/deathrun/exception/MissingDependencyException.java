package pl.mrstudios.deathrun.exception;

public class MissingDependencyException extends RuntimeException {

    public MissingDependencyException(String message) {
        super(message);
    }

}
