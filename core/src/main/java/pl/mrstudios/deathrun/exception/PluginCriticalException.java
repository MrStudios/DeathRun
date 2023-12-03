package pl.mrstudios.deathrun.exception;

public class PluginCriticalException extends RuntimeException {

    public PluginCriticalException(String message) {
        super(message);
    }

    public PluginCriticalException(String message, Throwable cause) {
        super(message, cause);
    }

}
