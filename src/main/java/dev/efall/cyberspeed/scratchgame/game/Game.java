package dev.efall.cyberspeed.scratchgame.game;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.GameConfig;
import dev.efall.cyberspeed.scratchgame.reward.RewardCalculator;
import dev.efall.cyberspeed.scratchgame.wincombination.WinCombinationEvaluator;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Data
public class Game {

    private final GameConfig config;
    private final WinCombinationEvaluator evaluator;
    private final RewardCalculator calculator;

    private final String[][] matrix;
    private final String bonusSymbol;

    public Game(GameConfig config, WinCombinationEvaluator evaluator, RewardCalculator calculator, String[][] matrix, String bonusSymbol) {
        this.config = config;
        this.evaluator = evaluator;
        this.calculator = calculator;
        this.matrix = matrix;
        this.bonusSymbol = bonusSymbol;
    }

    /***
     * Plays the game and returns the result of the game incliuding the reward
     * @param betAmount Amount to bet
     * @return GameResult
     */
    public GameResult play(BigDecimal betAmount) {
        // Find winning combinations
        HashMap<String, List<String>> appliedWinningConditions = evaluator.evaluate(config.getWinCombinations(), matrix);

        // Calculate reward
        BigDecimal reward = calculator.calculate(
                betAmount,
                appliedWinningConditions,
                bonusSymbol);

        // Return result
        return new GameResult(
                matrix,
                reward,
                appliedWinningConditions,
                bonusSymbol);
    }
}
