package io.github.eliaspinheiropereira.copilot.validator;

import io.github.eliaspinheiropereira.copilot.dto.request.PessoaRequest;
import io.github.eliaspinheiropereira.copilot.exceptions.PessoaJaExisteException;
import io.github.eliaspinheiropereira.copilot.model.Pessoa;
import io.github.eliaspinheiropereira.copilot.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PessoaValidator {

    private final PessoaRepository pessoaRepository;

    public void validar(PessoaRequest pessoaRequest) {
        Optional<Pessoa> buscarPessoaPorEmail = this.pessoaRepository.findByCpf(pessoaRequest.cpf());

        if(buscarPessoaPorEmail.isPresent()){
            throw new PessoaJaExisteException("Este usuário já existe no banco de dados");
        }
    }

}

