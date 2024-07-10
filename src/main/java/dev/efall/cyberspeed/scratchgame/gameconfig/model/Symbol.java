package dev.efall.cyberspeed.scratchgame.gameconfig.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Symbol {

    public enum Type {
        @JsonProperty("standard") STANDARD,
        @JsonProperty("bonus") BONUS
    }

    public enum Impact {
        @JsonProperty("multiply_reward") MULTIPLY_REWARD,
        @JsonProperty("extra_bonus") EXTRA_BONUS,
        @JsonProperty("miss") MISS
    }

    private BigDecimal rewardMultiplier;
    private BigDecimal extra;
    private Type type;
    private Impact impact;
}
