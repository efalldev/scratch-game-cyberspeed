package dev.efall.cyberspeed.scratchgame.wincombination.evaluator;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SameSymbolEvaluatorTest {

    private SameSymbolsEvaluator sut;
    private Map<String, WinCombination> winCombination;

    @BeforeEach
    void setUp() {
        winCombination = new HashMap<>();
        winCombination.put("same_symbol_4_times", toWinCombination(4));
        winCombination.put("same_symbol_5_times", toWinCombination(5));
        winCombination.put("same_symbol_6_times", toWinCombination(6));
        winCombination.put("same_symbol_7_times", toWinCombination(7));

        sut = new SameSymbolsEvaluator();
    }

    @Test
    void evaluate_threeSymbols_returnZeroMatch() {
        // Arrange
        String[][] matrix = {
                {"A", "B", "C"},
                {"D", "A", "B"},
                {"A", "H", "I"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void evaluate_fourSymbols_returnOneMatch() {
        // Arrange
        String[][] matrix = {
                {"A", "A", "C"},
                {"D", "A", "B"},
                {"A", "H", "I"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("A"));
        Assertions.assertEquals(result.get("A"), "same_symbol_4_times");
    }

    @Test
    void evaluate_fourSymbolsFiveSymbols_returnTwoMatch() {
        // Arrange
        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "B", "B"},
                {"B", "A", "B"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsKey("A"));
        Assertions.assertEquals(result.get("A"), "same_symbol_4_times");
        Assertions.assertTrue(result.containsKey("B"));
        Assertions.assertEquals(result.get("B"), "same_symbol_5_times");
    }

    @Test
    void evaluate_nineSymbols_returnOneMatch() {
        // Arrange
        String[][] matrix = {
                {"A", "A", "A"},
                {"A", "A", "A"},
                {"A", "A", "A"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("A"));
        Assertions.assertEquals(result.get("A"), "same_symbol_7_times");
    }

    private WinCombination toWinCombination(int count) {
        return new WinCombination(
                BigDecimal.ONE,
                WinCombination.When.SAME_SYMBOLS,
                count,
                "same_symbols",
                null);
    }
}
