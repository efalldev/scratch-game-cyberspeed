package dev.efall.cyberspeed.scratchgame.game;

import dev.efall.cyberspeed.scratchgame.gameconfig.GameConfigReader;
import dev.efall.cyberspeed.scratchgame.gameconfig.model.GameConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GameFactoryTest {

    private GameFactory sut;

    @BeforeEach
    void setUp() throws IOException {
        Random random = new Random(0);
        GameConfigReader reader = new GameConfigReader();
        GameConfig config = reader.readConfigFromResources("/config.json");

        sut = new GameFactory(config, random);
    }

    @Test
    void create_3x3_returnsOk() {
        // Arrange

        // Act
        Game actual = sut.create();

        // Assert
        Assertions.assertEquals(actual.getConfig().getRows(), actual.getMatrix().length);
        Assertions.assertEquals(actual.getConfig().getColumns(), actual.getMatrix()[0].length);
        Assertions.assertTrue(Arrays.stream(actual.getMatrix())
                .flatMap(Arrays::stream)
                .noneMatch(Objects::isNull));
    }
}
