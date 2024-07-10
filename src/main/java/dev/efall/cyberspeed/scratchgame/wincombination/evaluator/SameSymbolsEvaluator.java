package dev.efall.cyberspeed.scratchgame.wincombination.evaluator;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class SameSymbolsEvaluator implements WinCombinationEvaluator {

    private final String group = "same_symbols";
    private final String when = "same_symbols";

    @Override
    public Map<String, String> evaluate(String[][] matrix, Map<String, WinCombination> winCombinations) {
        Map<String, String> result = new HashMap<>();

        // Count occurrences per symbol
        Map<String, Integer> countMap = Arrays.stream(matrix)
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.summingInt(symbol -> 1)
                ));

        // Evaluate same symbol win-combinations and return the one with higiest count
        for(Map.Entry<String, Integer> entry : countMap.entrySet()) {
            evaluateSameSymbol(entry.getValue(), winCombinations).ifPresent(winCombination -> result.put(entry.getKey(), winCombination));
        }

        return result;
    }

    private Optional<String> evaluateSameSymbol(int count, Map<String, WinCombination> winCombinations) {
        return winCombinations.entrySet().stream()
                .filter(winCombination -> count >= winCombination.getValue().getCount())
                .max(Comparator.comparing(w -> w.getValue().getCount()))
                .map(Map.Entry::getKey);
    }
}
