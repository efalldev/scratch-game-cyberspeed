package dev.efall.cyberspeed.scratchgame.gameconfig;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.GameConfig;
import dev.efall.cyberspeed.scratchgame.gameconfig.model.Probability;
import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GameConfigValidator {

    /***
     * Performs basic validation and consistency of the config file
     * @param config The config file
     */
    public void validate(GameConfig config) {
        validateMatrixSize(config);
        validateProbabilities(config);
        validateWinCombinations(config);
    }

    private void validateMatrixSize(GameConfig config) {
        // Game rows should be > 0
        if(config.getRows() <= 0) {
            throw new GameConfigValidationException("Invalid game size, rows should be > 0 :: rows=" + config.getRows());
        }

        // Game columns should be > 0
        if(config.getColumns() <= 0) {
            throw new GameConfigValidationException("Invalid game size, columns should be > 0 :: columns=" + config.getColumns());
        }
    }

    private void validateProbabilities(GameConfig config) {
        boolean[][] probabilityExists = new boolean[config.getRows()][config.getColumns()];

        for(Probability p : config.getProbabilities().getStandardSymbols()) {
            // Dimensions of matrix and symbol probability row must match
            if(p.getRow() >= config.getRows()) {
                throw new GameConfigValidationException("Invalid standard probability row, should be <= matrix row size :: row=" + p.getRow());
            }

            // Dimensions of matrix and symbol probability column must match
            if(p.getColumn() >= config.getColumns()) {
                throw new GameConfigValidationException("Invalid standard probability column, should be <= matrix row size :: column=" + p.getColumn());
            }

            // Standard symbol probability should be >= 0
            for(Map.Entry<String, Integer> symbolEntry : p.getSymbols().entrySet()) {
                if(symbolEntry.getValue() < 0) {
                    throw new GameConfigValidationException("Invalid standard probability value, should be >= 0 :: symbol=" + symbolEntry.getKey() + ", value=" + symbolEntry.getValue());
                }
            }

            probabilityExists[p.getRow()][p.getColumn()] = true;
        }

        // Bonus symbol probability should be >= 0
        for(Map.Entry<String, Integer> symbolEntry : config.getProbabilities().getBonusSymbols().getSymbols().entrySet()) {
            if(symbolEntry.getValue() < 0) {
                throw new GameConfigValidationException("Invalid bonus probability value, should be >= 0 :: symbol=" + symbolEntry.getKey() + ", value=" + symbolEntry.getValue());
            }
        }

        // Every cell in the matrix should have a probability
        for(int row = 0; row < config.getRows(); row++ ) {
            for(int column = 0; column < config.getColumns(); column++ ) {
                if(!probabilityExists[row][column]) {
                    throw new GameConfigValidationException("Missing probability :: row="  + config.getColumns() + ", column=" + config.getColumns());
                }
            }
        }
    }

    private void validateWinCombinations(GameConfig config) {
        for(Map.Entry<String, WinCombination> w : config.getWinCombinations().entrySet()) {
            WinCombination winCombination = w.getValue();

            // When should not be null
            if(winCombination.getWhen() == null) {
                throw new GameConfigValidationException("Missing win-combination when :: name=" + w.getKey());
            }

            // Group should not be null
            if(winCombination.getGroup() == null || winCombination.getGroup().isEmpty()) {
                throw new GameConfigValidationException("Missing win-combination group :: name=" + w.getKey());
            }

            // RewardMultiplier should be >= 0
            if(winCombination.getRewardMultiplier().compareTo(BigDecimal.ZERO) < 0) {
                throw new GameConfigValidationException("Invalid win-combination reward-multiplier, should be > 0 :: name=" + w.getKey() + ", multiplier=" + winCombination.getRewardMultiplier());
            }

            if(winCombination.getWhen() == WinCombination.When.SAME_SYMBOLS) {
                // Count should be > 0
                if(winCombination.getCount() <= 0) {
                    throw new GameConfigValidationException("Invalid win-combination count, should be >= 0 :: name=" + w.getKey() + ", count=" + winCombination.getCount());
                }
            }

            if(winCombination.getWhen() == WinCombination.When.LINEAR_SYMBOLS) {
                // CoveredAreas should not be null for LINEAR_SYMBOLS
                if(winCombination.getCoveredAreas() == null || winCombination.getCoveredAreas().isEmpty()) {
                    throw new GameConfigValidationException("Missing win-combination covered-areas :: name=" + w.getKey());
                }

                for(List<String> areas : winCombination.getCoveredAreas()) {
                    for(String area : areas) {
                        String[] split = area.split(":");

                        // CoveredAreas should be in the format x:x
                        if(split.length != 2) {
                            throw new GameConfigValidationException("Invalid covered-area :: name=" + w.getKey() +  ", area=" + Arrays.toString(split));
                        }

                        int row = Integer.parseInt(split[0]);
                        int column = Integer.parseInt(split[1]);

                        // Row must be within the bounds of the game matrix
                        if(row < 0 || row > config.getRows()) {
                            throw new GameConfigValidationException("Invalid covered-area row :: name=" + w.getKey() +  ", row=" + row);
                        }

                        // Column must be within the bounds of the game matrix
                        if(column < 0 || column > config.getColumns()) {
                            throw new GameConfigValidationException("Invalid covered-area row :: name=" + w.getKey() +  ", row=" + row);
                        }
                    }
                }
            }
        }
    }
}
