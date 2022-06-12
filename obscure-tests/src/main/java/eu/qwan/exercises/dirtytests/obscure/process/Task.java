package eu.qwan.exercises.dirtytests.obscure.process;

public abstract class Task<TaskDefinitionType, StateType extends State> {
  protected StateType state;

  public abstract boolean isStateChangeAllowed(StateType taskState);
  public abstract TaskDefinitionType getDefinition();

  public StateType getState() {
    return state;
  }

  public void setState(StateType newState) {
    state = newState;
  }
}
