package pl.mrstudios.deathrun.exception;

import org.jetbrains.annotations.NotNull;

public class PluginCriticalException extends RuntimeException {

    public PluginCriticalException(@NotNull String message) {
        super(message);
    }

    public PluginCriticalException(
            @NotNull String message,
            @NotNull Throwable cause
    ) {
        super(message, cause);
    }

}
