package eu.qwan.exercises.dirtytests.obscure.process;

public enum AssignmentTaskState implements State {
  INITIAL,
  NOMINATED,
  ASSIGNED,
  DECLINED;

  public String getName() {
    return name();
  }
}
