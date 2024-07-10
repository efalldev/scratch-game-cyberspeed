package dev.efall.cyberspeed.scratchgame.wincombination.evaluator;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;

import java.util.Map;

public interface WinCombinationEvaluator {

    String getGroup();

    String getWhen();

    Map<String, String> evaluate(String[][] matrix, Map<String, WinCombination> winCombinations);
}
