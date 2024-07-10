package dev.efall.cyberspeed.scratchgame.wincombination.evaluator;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public class HorizontalLinearSymbolsEvaluatorTest {

    private HorizontalLinearSymbolsEvaluator sut;
    private Map<String, WinCombination> winCombination;

    @BeforeEach
    void setUp() {
        winCombination = Map.of(
                "same_symbols_horizontally",
                new WinCombination(
                        BigDecimal.ONE,
                        WinCombination.When.LINEAR_SYMBOLS,
                        0,
                        "horizontally_linear_symbols",
                        Arrays.asList(
                                Arrays.asList("0:0", "0:1", "0:2"),
                                Arrays.asList("1:0", "1:1", "1:2"),
                                Arrays.asList("2:0", "2:1", "2:2"))
                ));

        sut = new HorizontalLinearSymbolsEvaluator();
    }

    @Test
    void evaluate_oneMatchingRow_returnOneMatch() {
        // Arrange
        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "C", "C"},
                {"B", "D", "D"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("A"));
        Assertions.assertEquals(result.get("A"), "same_symbols_horizontally");
    }

    @Test
    void evaluate_twoMatchingRows_returnTwoMatches() {
        // Arrange
        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "C", "C"},
                {"D", "D", "D"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsKey("A"));
        Assertions.assertEquals(result.get("A"), "same_symbols_horizontally");
        Assertions.assertTrue(result.containsKey("D"));
        Assertions.assertEquals(result.get("D"), "same_symbols_horizontally");
    }

    @Test
    void evaluate_twoMatchingRowsSameSymbol_returnOneMatch() {
        // Arrange
        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "C", "C"},
                {"A", "A", "A"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("A"));
        Assertions.assertEquals(result.get("A"), "same_symbols_horizontally");
    }

    @Test
    void evaluate_zeroMatchingRows_returnEmptyMap() {
        // Arrange
        String[][] matrix = {
                {"A", "C", "C"},
                {"B", "C", "C"},
                {"A", "C", "C"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(0, result.size());
    }
}
