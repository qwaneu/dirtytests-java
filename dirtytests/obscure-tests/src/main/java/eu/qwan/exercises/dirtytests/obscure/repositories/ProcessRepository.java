package eu.qwan.exercises.dirtytests.obscure.repositories;

import eu.qwan.exercises.dirtytests.obscure.process.Process;
import eu.qwan.exercises.dirtytests.obscure.process.ProcessDefinition;
import eu.qwan.exercises.dirtytests.obscure.process.State;
import eu.qwan.exercises.dirtytests.obscure.process.Task;

import java.util.List;

public interface ProcessRepository {
  Process findByDefinitionAndBusinessObject(ProcessDefinition definition, String businessObject);

  void save(Process process, Task<?, ? extends State> task, List<String> theWhys);
}
