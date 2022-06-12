package eu.qwan.exercises.dirtytests.obscure.process;

import java.util.HashMap;
import java.util.Map;

/**
 Process represents the whole process a shipment goes through, from leaving the
 factory, through all involved distribution centres to the final destination.
 A shipment's process involves multiple tasks, like assigning a new carrier.
 Each task has one or more states.
 */
public class Process<TaskDefinitionType, StateType extends State> {
  private ProcessDefinition processDefinition;
  private String businessObject;
  private final Map<TaskDefinitionType, Task<TaskDefinitionType, StateType>> tasks = new HashMap<>();

  public Process(ProcessDefinition processDefinition, String businessObject, Map<TaskDefinitionType, Task<TaskDefinitionType, StateType>> tasks) {
    this.processDefinition = processDefinition;
    this.businessObject = businessObject;
    this.tasks.putAll(tasks);
  }

  public Task<TaskDefinitionType, StateType> getTask(TaskDefinitionType taskDefinition) {
    return this.tasks.get(taskDefinition);
  }
}
