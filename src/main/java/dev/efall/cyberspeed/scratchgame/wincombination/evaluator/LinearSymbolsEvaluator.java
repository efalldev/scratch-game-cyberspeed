package dev.efall.cyberspeed.scratchgame.wincombination.evaluator;

import dev.efall.cyberspeed.scratchgame.gameconfig.model.WinCombination;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LinearSymbolsEvaluator implements WinCombinationEvaluator {

    private record Result(boolean matched, String symbol) { }

    @Override
    public Map<String, String> evaluate(String[][] matrix, Map<String, WinCombination> winCombinations) {
        return winCombinations.entrySet().stream()
                .flatMap(entry ->
                        entry.getValue().getCoveredAreas().stream()
                                .map(area -> evaluateArea(matrix, area))
                                .filter(r -> r.matched)
                                .map(r -> new AbstractMap.SimpleEntry<>(r.symbol, entry.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existingValue, newValue) -> existingValue));
    }

    private Result evaluateArea(String[][] matrix, List<String> area) {
        Set<String> symbols =  area.stream()
                .map(a -> {
                    String[] split = a.split(":");
                    return matrix[Integer.parseInt(split[0])][Integer.parseInt(split[1])];
                })
                .collect(Collectors.toSet());

        return new Result(
                symbols.size() == 1,
                symbols.stream().findFirst().orElse(""));
    }
}
