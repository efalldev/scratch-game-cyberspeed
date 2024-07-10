package dev.efall.cyberspeed.scratchgame.wincombination.evaluator;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RtlDiagonalLinearSymbolsEvaluatorTest {

    private RtlDiagonalLinearSymbolsEvaluator sut;
    private Map<String, WinCombination> winCombination;

    @BeforeEach
    void setUp() {
        winCombination = Map.of(
                "same_symbols_diagonally_right_to_left",
                new WinCombination(
                        BigDecimal.ONE,
                        WinCombination.When.LINEAR_SYMBOLS,
                        0,
                        "rtl_diagonally_linear_symbols",
                        List.of(Arrays.asList("0:2", "1:1", "2:0"))
                ));

        sut = new RtlDiagonalLinearSymbolsEvaluator();
    }

    @Test
    void evaluate_matchingDiagonal_returnOneMatch() {
        // Arrange
        String[][] matrix = {
                {"A", "C", "A"},
                {"B", "A", "C"},
                {"A", "D", "C"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("A"));
        Assertions.assertEquals(result.get("A"), "same_symbols_diagonally_right_to_left");
    }

    @Test
    void evaluate_zeroMatchingDiagonal_returnEmptyMap() {
        // Arrange
        String[][] matrix = {
                {"A", "C", "C"},
                {"A", "A", "A"},
                {"B", "C", "A"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(0, result.size());
    }
}
