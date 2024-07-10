package dev.efall.cyberspeed.scratchgame.gameconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import dev.efall.cyberspeed.scratchgame.gameconfig.model.GameConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GameConfigReader {

    private final ObjectMapper objectMapper;
    private final GameConfigValidator validator;

    public GameConfigReader() {
        this(new GameConfigValidator());
    }

    public GameConfigReader(GameConfigValidator validator) {
        this.validator = validator;

        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    /***
     * Reads and validates a game config file.
     * @param filePath The config file path
     * @return A POJO representation of the config file.
     */
    public GameConfig readConfig(String filePath) throws IOException {
        File file = new File(filePath);

        GameConfig config = objectMapper.readValue(file, GameConfig.class);
        validator.validate(config);

        return config;
    }

    /***
     *
     * @param filePath Reads and validates a game config file from the classpath.
     * @return A POJO representation of the config file.
     */
    public GameConfig readConfigFromResources(String filePath) throws IOException {
        try (InputStream inputStream = GameConfigReader.class.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + filePath);
            }

            GameConfig config = objectMapper.readValue(inputStream, GameConfig.class);
            validator.validate(config);

            return config;
        }
    }
}
