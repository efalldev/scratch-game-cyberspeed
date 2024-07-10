package dev.efall.cyberspeed.scratchgame.gameconfig.model;

import lombok.Data;

import java.util.List;

@Data
public class Probabilities {

    private List<Probability> standardSymbols;
    private BonusSymbols bonusSymbols;
}
