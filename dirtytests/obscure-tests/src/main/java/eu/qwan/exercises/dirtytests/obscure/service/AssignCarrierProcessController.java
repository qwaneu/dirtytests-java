package eu.qwan.exercises.dirtytests.obscure.service;

import eu.qwan.exercises.dirtytests.obscure.process.Process;
import eu.qwan.exercises.dirtytests.obscure.process.State;
import eu.qwan.exercises.dirtytests.obscure.process.Task;
import eu.qwan.exercises.dirtytests.obscure.repositories.ProcessRepository;

import java.util.List;

public class AssignCarrierProcessController {
  private final ProcessRepository processRepository;

  public AssignCarrierProcessController(ProcessRepository processRepository) {
    this.processRepository = processRepository;
  }

  public <StateType extends State> void changeState(Process process, Task<?, StateType> task, StateType newState, List<String> theWhys) {
    task.setState(newState);
    processRepository.save(process, task, theWhys);
  }
}
