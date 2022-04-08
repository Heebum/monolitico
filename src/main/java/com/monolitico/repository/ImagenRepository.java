package com.monolitico.repository;

import com.monolitico.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
}
