package dev.efall.cyberspeed.scratchgame.gameconfig.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinCombination {

    public enum When {
        @JsonProperty("same_symbols") SAME_SYMBOLS,
        @JsonProperty("linear_symbols") LINEAR_SYMBOLS
    }

    private BigDecimal rewardMultiplier;
    private When when;
    private int count;
    private String group;
    private List<List<String>> coveredAreas;
}
