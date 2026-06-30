package io.github.eliaspinheiropereira.copilot.service;

import io.github.eliaspinheiropereira.copilot.dto.request.PessoaRequest;
import io.github.eliaspinheiropereira.copilot.dto.response.PessoaResponse;
import io.github.eliaspinheiropereira.copilot.exceptions.PessoaNaoEncontradaException;
import io.github.eliaspinheiropereira.copilot.mapper.PessoaMapper;
import io.github.eliaspinheiropereira.copilot.repository.PessoaRepository;
import io.github.eliaspinheiropereira.copilot.validator.PessoaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public PessoaResponse buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id)
                .map(this.pessoaMapper::toResponse)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Não foi possível buscar a pessoa, pois ela não existe no banco de dados"));
    }

    public Page<PessoaResponse> buscarTodasPessoas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pessoaRepository.findAll(pageable)
                .map(this.pessoaMapper::toResponse);
    }

    public void removerPessoa(Long id) {
        this.pessoaRepository.findById(id).ifPresentOrElse(
                pessoa -> {
                    this.pessoaRepository.deleteById(pessoa.getId());
                },
                () -> {
                    throw new PessoaNaoEncontradaException("Não foi possível remover a pessoa, pois ela não existe no banco de dados");
                }
        );

    }

    public void atualizarPessoa(Long id, PessoaRequest pessoaRequest) {
        this.pessoaRepository.findById(id).ifPresentOrElse(
                pessoa -> {
                    pessoa.setNome(pessoaRequest.nome());
                    pessoa.setEmail(pessoaRequest.email());
                    pessoa.setSenha(pessoaRequest.senha());
                    pessoa.setCpf(pessoaRequest.cpf());
                    pessoa.setTelefone(pessoaRequest.telefone());
                    this.pessoaRepository.save(pessoa);
                },
                () -> {
                    throw new PessoaNaoEncontradaException("Não foi possível atualizar a pessoa, pois ela não existe no banco de dados");
                }
        );
    }


}
