package dev.lmarchesoti.ClienteSimulacao.repositories;

import dev.lmarchesoti.ClienteSimulacao.model.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacaoRepository extends JpaRepository<Simulacao, Long> {
}
