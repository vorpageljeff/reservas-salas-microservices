package com.reservasala.reserva_sala.repository;

import com.reservasala.reserva_sala.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {}