package dev.efall.cyberspeed.scratchgame.gameconfig.model;

import lombok.Data;

import java.util.Map;

@Data
public class GameConfig {

    private int rows;
    private int columns;
    private Map<String, Symbol> symbols;
    private Probabilities probabilities;
    private Map<String, WinCombination> winCombinations;
}
