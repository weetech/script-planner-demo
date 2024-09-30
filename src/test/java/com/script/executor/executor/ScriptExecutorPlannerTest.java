package com.script.executor.executor;

import com.script.executor.entity.VulnerabilityScript;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

class ScriptExecutorPlannerTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideTestCases")
    void testPlanSequentialExecution(String testName, List<VulnerabilityScript> scripts, List<List<Integer>> possibleResults) {
        ScriptExecutorPlanner planner = new ScriptExecutorPlanner(scripts);
        var result = planner.planSequentialExecution();
        assertTrue(verifyResult(possibleResults, result), testName + " fails");
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of("Sequential Test",
                        List.of(
                                new VulnerabilityScript(1, List.of(2)),
                                new VulnerabilityScript(2, List.of(3)),
                                new VulnerabilityScript(3, List.of(4)),
                                new VulnerabilityScript(4, List.of(5)),
                                new VulnerabilityScript(5, List.of())
                        ),
                        List.of(List.of(5, 4, 3, 2, 1))
                ),
                Arguments.of("Parallel Test",
                        List.of(
                                new VulnerabilityScript(1, List.of()),
                                new VulnerabilityScript(2, List.of()),
                                new VulnerabilityScript(3, List.of())
                        ),
                        List.of(
                                List.of(3, 2, 1), List.of(3, 1, 2), List.of(2, 3, 1),
                                List.of(2, 1, 3), List.of(1, 2, 3), List.of(1, 3, 2)
                        )
                ),
                Arguments.of("Single Independent Script Test",
                        List.of(new VulnerabilityScript(2, List.of())),
                        List.of(List.of(2))
                ),
                Arguments.of("Empty Script List Test",
                        List.of(),
                        List.of(List.of())
                ),
                Arguments.of("Mixed Dependent Group",
                        List.of(
                                new VulnerabilityScript(1, List.of(2, 3)),
                                new VulnerabilityScript(2, List.of(3)),
                                new VulnerabilityScript(3, List.of()),
                                new VulnerabilityScript(4, List.of(1, 5)),
                                new VulnerabilityScript(5, List.of(2))
                        ),
                        List.of(
                                List.of(3, 2, 1, 5, 4),
                                List.of(3, 2, 5, 1, 4),
                                List.of(3, 2, 5, 1, 4)
                        )
                ),
                Arguments.of("Mixed Independent Group",
                        List.of(
                                new VulnerabilityScript(1, List.of(2, 3)),
                                new VulnerabilityScript(2, List.of(3)),
                                new VulnerabilityScript(3, List.of()),
                                new VulnerabilityScript(4, List.of(5)),
                                new VulnerabilityScript(5, List.of())
                        ),
                        List.of(
                                List.of(3, 2, 1, 5, 4), List.of(3, 2, 5, 1, 4),
                                List.of(3, 2, 5, 4, 1), List.of(3, 5, 2, 1, 4),
                                List.of(3, 5, 2, 4, 1), List.of(3, 5, 4, 2, 1),
                                List.of(5, 3, 2, 1, 4), List.of(5, 3, 2, 4, 1),
                                List.of(5, 3, 4, 2, 1), List.of(5, 4, 3, 2, 1)
                        )
                )
        );
    }

    private boolean verifyResult(List<List<Integer>> possibleResults, List<Integer> result) {
        return possibleResults.stream().anyMatch(
                possibleItem -> possibleItem.equals(result)
        );
    }
}