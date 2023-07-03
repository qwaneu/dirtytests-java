package eu.qwan.exercises.dirtytests.obscure.repositories;

import eu.qwan.exercises.dirtytests.obscure.domain.TransportOrganisation;

public interface OrganisationRepository {
    TransportOrganisation findByOrn(String orn);
}
