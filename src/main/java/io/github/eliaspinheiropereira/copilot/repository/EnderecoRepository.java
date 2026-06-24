package io.github.eliaspinheiropereira.copilot.repository;

import io.github.eliaspinheiropereira.copilot.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
