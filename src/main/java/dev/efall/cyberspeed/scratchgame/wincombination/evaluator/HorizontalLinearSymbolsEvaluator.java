package dev.efall.cyberspeed.scratchgame.wincombination.evaluator;

import lombok.Getter;

@Getter
public class HorizontalLinearSymbolsEvaluator extends LinearSymbolsEvaluator {

    private record Result(boolean matched, String symbol) { }

    private final String group = "horizontally_linear_symbols";
    private final String when = "linear_symbols";
}
