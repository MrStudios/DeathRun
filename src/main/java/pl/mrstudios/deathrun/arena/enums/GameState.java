package pl.mrstudios.deathrun.arena.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameState {

    WAITING(),
    STARTING(),
    PLAYING(),
    ENDING();

}
