package dev.efall.cyberspeed.scratchgame.wincombination.evaluator;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LtrDiagonalLinearSymbolsEvaluatorTest {

    private LtrDiagonalLinearSymbolsEvaluator sut;
    private Map<String, WinCombination> winCombination;

    @BeforeEach
    void setUp() {
        winCombination = Map.of(
                "same_symbols_diagonally_left_to_right",
                new WinCombination(
                        BigDecimal.ONE,
                        WinCombination.When.LINEAR_SYMBOLS,
                        0,
                        "ltr_diagonally_linear_symbols",
                        List.of(Arrays.asList("0:0", "1:1", "2:2"))
                ));

        sut = new LtrDiagonalLinearSymbolsEvaluator();
    }

    @Test
    void evaluate_matchingDiagonal_returnOneMatch() {
        // Arrange
        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "A", "C"},
                {"B", "D", "A"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("A"));
        Assertions.assertEquals(result.get("A"), "same_symbols_diagonally_left_to_right");
    }

    @Test
    void evaluate_zeroMatchingDiagonal_returnEmptyMap() {
        // Arrange
        String[][] matrix = {
                {"A", "C", "A"},
                {"B", "A", "C"},
                {"A", "C", "C"}
        };

        // Act
        Map<String, String> result = sut.evaluate(matrix, winCombination);

        // Assert
        Assertions.assertEquals(0, result.size());
    }
}
