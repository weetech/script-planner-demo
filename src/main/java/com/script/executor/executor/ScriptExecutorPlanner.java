package com.script.executor.executor;

import com.script.executor.entity.VulnerabilityScript;

import java.util.*;

public class ScriptExecutorPlanner {
    private Map<Integer, VulnerabilityScript> scriptStore = new HashMap<>();
    private Set<Integer> visited = new HashSet<>();
    private List<Integer> executionOrder = new ArrayList<>();

    public ScriptExecutorPlanner(List<VulnerabilityScript> scriptList) {
        for (VulnerabilityScript script: scriptList) {
            this.scriptStore.put(script.getScriptId(), script);
        }
    }

    List<Integer> planSequentialExecution() {
        for (int scriptId: scriptStore.keySet()) {
            if (!visited.contains(scriptId)) {
                depthFistSearch(scriptId);
            }
        }
        return executionOrder;
    }

    private void depthFistSearch(int scriptId) {
        visited.add(scriptId);
        VulnerabilityScript script = scriptStore.get(scriptId);

        for (int dependencyScriptId: script.getDependencies()) {
            if (!visited.contains(dependencyScriptId)) {
                depthFistSearch(dependencyScriptId);
            }
        }

        executionOrder.add(scriptId);
    }
}
