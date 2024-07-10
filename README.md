# scratch-game-cyberspeed
Coding assignment for Cyberspeed

## Notes
- Using Java 17.
- Support for all OPTIONAL features.
- Support for sizes other than 3x3, make sure other parts of the config reflects this e.g. every cell in the matrix needs to have an entry in the probability field and all covered-areas need to be correct.
- Only one bonus reward cell is generated per game. I found it unclear if I should support multiple bonus rewards.
- The sample config.json has rows=4 and columns=4 despite it clearly is meant to be 3x3.
- One of the examples in problem description calculates a reward of 6600. In the details it reaches this number by using a multiplier of 5 for the same_symbol_5_times win-combination. According to the config file this multiplier should be 2.
- To simplify validation I would create a json schema for the config file. I decided to not do it for this assignment because do not believe this is what the test is about.

## Usage
```java -jar scratch-game-cyberspeed-1.0.0-SNAPSHOT.jar --config config.json --betting-amount 100```

## Sample result
```json
{
  "matrix" : [ [ "C", "E", "E" ], [ "E", "B", "D" ], [ "D", "F", "5x" ] ],
  "reward" : 600.0,
  "appliedWinningConditions" : {
    "E" : [ "same_symbol_3_times" ]
  },
  "appliedBonusSymbol" : "5x"
}
```