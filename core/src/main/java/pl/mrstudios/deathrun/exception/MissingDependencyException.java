package pl.mrstudios.deathrun.exception;

import org.jetbrains.annotations.NotNull;

public class MissingDependencyException extends RuntimeException {

    public MissingDependencyException(
            @NotNull String message
    ) {
        super(message);
    }

}
