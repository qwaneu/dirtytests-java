package eu.qwan.exercises.dirtytests.obscure.process;

import java.util.ArrayList;
import java.util.List;

import static eu.qwan.exercises.dirtytests.obscure.process.AssignmentTaskState.ASSIGNED;
import static eu.qwan.exercises.dirtytests.obscure.process.AssignmentTaskState.DECLINED;
import static eu.qwan.exercises.dirtytests.obscure.process.AssignmentTaskState.NOMINATED;
import static java.util.Arrays.asList;

public class AssignmentCarrierTask extends Task<AssignmentTaskDefinition, AssignmentTaskState> {
    @Override
    public boolean isStateChangeAllowed(AssignmentTaskState taskState) {
        List<AssignmentTaskState> allowedStates = new ArrayList<>();
        switch (state) {
            case INITIAL:
                allowedStates = asList(NOMINATED, DECLINED);
                break;
            case NOMINATED:
                allowedStates = asList(ASSIGNED, DECLINED);
                break;
            case DECLINED:
                allowedStates = asList(NOMINATED);
            case ASSIGNED:
                break;
        }
        return allowedStates.contains(taskState);
    }

    @Override
    public AssignmentTaskDefinition getDefinition() {
        return AssignmentTaskDefinition.ASSIGN_CARRIER;
    }
}
