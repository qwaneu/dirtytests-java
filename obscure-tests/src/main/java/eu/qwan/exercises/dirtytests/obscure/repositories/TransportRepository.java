package eu.qwan.exercises.dirtytests.obscure.repositories;

import eu.qwan.exercises.dirtytests.obscure.domain.Transport;

public interface TransportRepository {
  Transport findByTrn(Object any);

  void save(Transport transport);
}
