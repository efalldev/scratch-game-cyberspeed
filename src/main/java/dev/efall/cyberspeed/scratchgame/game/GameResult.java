package dev.efall.cyberspeed.scratchgame.game;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class GameResult {

    private final String[][] matrix;
    private final BigDecimal reward;
    private final Map<String, List<String>> appliedWinningConditions;
    private final String appliedBonusSymbol;
}
