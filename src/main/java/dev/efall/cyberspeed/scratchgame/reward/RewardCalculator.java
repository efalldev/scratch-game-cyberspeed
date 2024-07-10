package dev.efall.cyberspeed.scratchgame.reward;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.GameConfig;
import dev.efall.cyberspeed.scratchgame.gameconfig.model.Symbol;
import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class RewardCalculator {

    private final GameConfig gameConfig;

    /***
     * Calculates the final reward for a game
     * @param betAmount The bet amount
     * @param winConditions The win conditions found in the game matrix
     * @param bonusSymbol The conus symbol in the game matrix
     */
    public BigDecimal calculate(BigDecimal betAmount, Map<String, List<String>> winConditions, String bonusSymbol) {
        AtomicReference<BigDecimal> reward = new AtomicReference<>(BigDecimal.ZERO);

        winConditions.forEach((key, value) -> {
            BigDecimal rewardMultiplier = value.stream()
                    .map(winCombination -> gameConfig.getWinCombinations().get(winCombination))
                    .map(WinCombination::getRewardMultiplier)
                    .reduce(BigDecimal.ONE, BigDecimal::multiply);

            BigDecimal symbolMultiplier = gameConfig.getSymbols().get(key).getRewardMultiplier();

            reward.updateAndGet(currentReward -> currentReward.add(betAmount.multiply(rewardMultiplier.multiply(symbolMultiplier))));
        });

        return withBonusReward(reward.get(), bonusSymbol);
    }

    private BigDecimal withBonusReward(BigDecimal reward, String bonusSymbol) {
        Symbol symbol = gameConfig.getSymbols().get(bonusSymbol);

        return switch (symbol.getImpact()) {
            case MULTIPLY_REWARD -> reward.multiply(symbol.getRewardMultiplier());
            case EXTRA_BONUS -> reward.add(symbol.getExtra());
            case MISS -> BigDecimal.ZERO;
        };
    }
}
