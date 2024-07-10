package dev.efall.cyberspeed.scratchgame.game;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.GameConfig;
import dev.efall.cyberspeed.scratchgame.gameconfig.model.Probability;
import dev.efall.cyberspeed.scratchgame.reward.RewardCalculator;
import dev.efall.cyberspeed.scratchgame.wincombination.WinCombinationEvaluator;
import dev.efall.cyberspeed.scratchgame.wincombination.evaluator.*;

import java.util.Map;
import java.util.Random;

public class GameFactory {

    private final Random random;
    private final GameConfig config;
    private final WinCombinationEvaluator evaluator;
    private final RewardCalculator calculator;

    public GameFactory(GameConfig config, Random random) {
        this.config = config;
        this.random = random;

        calculator = new RewardCalculator(config);
        evaluator = new WinCombinationEvaluator.Builder()
                .withEvaluator(new SameSymbolsEvaluator())
                .withEvaluator(new HorizontalLinearSymbolsEvaluator())
                .withEvaluator(new VerticalLinearSymbolsEvaluator())
                .withEvaluator(new LtrDiagonalLinearSymbolsEvaluator())
                .withEvaluator(new RtlDiagonalLinearSymbolsEvaluator())
                .build();
    }

    /***
     * Factory class for instantiating a new Game based on the config
     * @return New Game
     */
    public Game create() {
        String[][] matrix = new String[config.getRows()][config.getColumns()];

        // Create matrix with standard symbols
        for(Probability p : config.getProbabilities().getStandardSymbols()) {
            matrix[p.getRow()][p.getColumn()] = getWeightedRandomSymbol(p.getSymbols());
        }

        // Set bonus symbol on a random place in the matrix
        String bonusSymbol = getWeightedRandomSymbol(config.getProbabilities().getBonusSymbols().getSymbols());
        matrix[random.nextInt(matrix.length)][random.nextInt(matrix[0].length)] = bonusSymbol;

        return new Game(config, evaluator, calculator, matrix, bonusSymbol);
    }

    private String getWeightedRandomSymbol(Map<String, Integer> symbols) {
        int totalWeight = symbols.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        int randomNumber = random.nextInt(totalWeight) + 1;
        int cumulativeWeight = 0;

        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            cumulativeWeight += entry.getValue();

            if (randomNumber <= cumulativeWeight) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("Unreachable");
    }
}
