package com.reservasala.reserva_sala.repository;

import com.reservasala.reserva_sala.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {}
