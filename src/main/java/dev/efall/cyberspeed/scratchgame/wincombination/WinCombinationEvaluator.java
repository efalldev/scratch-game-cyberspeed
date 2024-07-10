package dev.efall.cyberspeed.scratchgame.wincombination;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WinCombinationEvaluator {

    private final Map<String, dev.efall.cyberspeed.scratchgame.wincombination.evaluator.WinCombinationEvaluator> evaluators;

    /***
     * Finds all win conditions from the game matrix using the win combinations from the config file
     * @param winCombinations The win combinations from the config file
     * @param matrix The game matrix
     * @return A map of all win conditions found.
     */
    public HashMap<String, List<String>> evaluate(Map<String, WinCombination> winCombinations, String[][] matrix) {
        HashMap<String, List<String>> appliedWinningConditions = new HashMap<>();

        winCombinations.entrySet().stream()
                // Group by 'group' field
                .collect(Collectors.groupingBy(
                        entry -> entry.getValue().getGroup(),
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                // For each group, call relevant evaluator
                .forEach((group, entries) -> {
                    Map<String, String> result = evaluators.computeIfAbsent(group, k -> {
                        throw new RuntimeException("Missing evaluator for group=" + group);
                    }).evaluate(matrix, entries);

                    result.forEach((key, value) -> appliedWinningConditions
                            .computeIfAbsent(key, k -> new ArrayList<>())
                            .add(value));
                });

        return appliedWinningConditions;
    }

    public static class Builder {

        private final Map<String, dev.efall.cyberspeed.scratchgame.wincombination.evaluator.WinCombinationEvaluator> evaluators = new HashMap<>();

        public Builder withEvaluator(dev.efall.cyberspeed.scratchgame.wincombination.evaluator.WinCombinationEvaluator evaluator) {
            evaluators.put(evaluator.getGroup(), evaluator);
            return this;
        }

        public WinCombinationEvaluator build() {
            return new WinCombinationEvaluator(evaluators);
        }
    }
}
