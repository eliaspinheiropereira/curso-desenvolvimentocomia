package io.github.eliaspinheiropereira.copilot.service;

import io.github.eliaspinheiropereira.copilot.dto.request.PessoaRequest;
import io.github.eliaspinheiropereira.copilot.mapper.PessoaMapper;
import io.github.eliaspinheiropereira.copilot.repository.PessoaRepository;
import io.github.eliaspinheiropereira.copilot.validator.PessoaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;
    private final PessoaValidator pessoaValidator;

    public void salvarPessoa(PessoaRequest pessoaRequest) {
        this.pessoaValidator.validar(pessoaRequest);
        this.pessoaRepository.save(this.pessoaMapper.toEntity(pessoaRequest));
    }


}
