package dev.efall.cyberspeed.scratchgame.reward;

import dev.efall.cyberspeed.scratchgame.gameconfig.GameConfigReader;
import dev.efall.cyberspeed.scratchgame.gameconfig.model.GameConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RewardCalculatorTest {

    private RewardCalculator sut;

    @BeforeEach
    void setUp() throws IOException {
        GameConfigReader reader = new GameConfigReader();
        GameConfig config = reader.readConfigFromResources("/config.json");
        sut = new RewardCalculator(config);
    }

    @Test
    void calculate_sameSymbol5AndVerticallySameSymbol3AndVertically_return3600() {
        // Arrange
        BigDecimal betAmount = new BigDecimal(100);
        Map<String, List<String>> winConditions = Map.of(
                "A", Arrays.asList("same_symbol_5_times", "same_symbols_vertically"),
                "B", Arrays.asList("same_symbol_3_times", "same_symbols_vertically"));

        // Act
        BigDecimal actual = sut.calculate(betAmount, winConditions, "+1000");

        // Assert
        Assertions.assertEquals(BigDecimal.valueOf(3600), actual);
    }

    @Test
    void calculate_ltrDiagonalAndThreeSymbols_return3000() {
        // Arrange
        BigDecimal betAmount = new BigDecimal(100);
        Map<String, List<String>> winConditions = Map.of(
                "B", List.of("same_symbol_3_times"));

        // Act
        BigDecimal actual = sut.calculate(betAmount, winConditions, "10x");

        // Assert
        Assertions.assertEquals(BigDecimal.valueOf(3000), actual);
    }

    @Test
    void calculate_miss_return0() {
        // Arrange
        BigDecimal betAmount = new BigDecimal(100);
        Map<String, List<String>> winConditions = Map.of(
                "A", Arrays.asList("same_symbol_5_times", "same_symbols_vertically"));

        // Act
        BigDecimal actual = sut.calculate(betAmount, winConditions, "MISS");

        // Assert
        Assertions.assertEquals(BigDecimal.ZERO, actual);
    }

    @Test
    void calculate_onlyAddBonus_return500() {
        // Arrange
        BigDecimal betAmount = new BigDecimal(100);
        Map<String, List<String>> winConditions = Map.of();

        // Act
        BigDecimal actual = sut.calculate(betAmount, winConditions, "+500");

        // Assert
        Assertions.assertEquals(BigDecimal.valueOf(500), actual);
    }
}
