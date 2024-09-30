## This is a demo project for Script Execution Planner

### Problem statement

Let's say I have a database of scripts. Each script has an arbitrary number of dependencies. The dependencies are expressed as a list of s that need to be executed before a given script. There are no circular dependencies. I want to come up with an execution plan so that I can run all of the scripts in a sane order.

### Proposed Solution

Tracing Script dependency should result it Directed Graphs Data scturcure. (One or Many disconnected graphs).
We can do depth first search on them to get the required result.

### Code Structure

This is a maven based project. So code structure is similar to other maven projects.

Code has 2 files containing classes, `VulnerabilityScript` and `ScriptExecutorPlanner` and also there are test cases in `ScriptExecutorPlannerTest` class for `ScriptExecutorPlanner` class.

### Test Cases execution

Run below code to execute test cases
```
mvn test
```