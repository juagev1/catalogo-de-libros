package com.literalura.literalura.repository;


import com.literalura.literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNacimientoBeforeAndFallecimientoAfter(int nacimiento, int fallecimiento);
}
