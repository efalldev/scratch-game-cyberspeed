package dev.efall.cyberspeed.scratchgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.efall.cyberspeed.scratchgame.game.Game;
import dev.efall.cyberspeed.scratchgame.game.GameFactory;
import dev.efall.cyberspeed.scratchgame.game.GameResult;
import dev.efall.cyberspeed.scratchgame.gameconfig.GameConfigReader;
import dev.efall.cyberspeed.scratchgame.gameconfig.model.GameConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

public class ScratchGameRunner {

    public static void main(String[] args) throws IOException {
        String configFilePath = null;
        int bettingAmount = 0;

        // Read CLI arguments
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--config":
                    if (i + 1 < args.length) {
                        configFilePath = args[++i];
                    } else {
                        System.err.println("Expected argument after --config");
                        return;
                    }
                    break;
                case "--betting-amount":
                    if (i + 1 < args.length) {
                        try {
                            bettingAmount = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid number for --betting-amount");
                            return;
                        }
                    } else {
                        System.err.println("Expected argument after --betting-amount");
                        return;
                    }
                    break;
                default:
                    System.err.println("Unknown argument: " + args[i]);
                    return;
            }
        }

        // Read config
        GameConfigReader reader = new GameConfigReader();
        GameConfig config = reader.readConfig(configFilePath);

        // Play game
        Random random = new Random();
        Game game = new GameFactory(config, random).create();
        GameResult result = game.play(BigDecimal.valueOf(bettingAmount));

        // Print result
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println(objectMapper.writeValueAsString(result));
    }
}
