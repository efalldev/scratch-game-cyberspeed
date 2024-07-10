package dev.efall.cyberspeed.scratchgame.gameconfig.model;

import lombok.Data;

import java.util.Map;

@Data
public class Probability {

    private int row;
    private int column;
    private Map<String, Integer> symbols;
}
