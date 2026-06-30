package io.github.eliaspinheiropereira.copilot.service;

import io.github.eliaspinheiropereira.copilot.dto.request.EnderecoRequest;
import io.github.eliaspinheiropereira.copilot.dto.response.EnderecoResponse;
import io.github.eliaspinheiropereira.copilot.exceptions.EnderecoNaoEncontradaException;
import io.github.eliaspinheiropereira.copilot.exceptions.PessoaNaoEncontradaException;
import io.github.eliaspinheiropereira.copilot.mapper.EnderecoMapper;
import io.github.eliaspinheiropereira.copilot.model.Endereco;
import io.github.eliaspinheiropereira.copilot.model.Pessoa;
import io.github.eliaspinheiropereira.copilot.repository.EnderecoRepository;
import io.github.eliaspinheiropereira.copilot.repository.PessoaRepository;
import io.github.eliaspinheiropereira.copilot.validator.PessoaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaRepository pessoaRepository;
    private final EnderecoMapper enderecoMapper;
    private final PessoaValidator pessoaValidator;


    public EnderecoResponse buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .map(this.enderecoMapper::toResponse)
                .orElseThrow(() -> new EnderecoNaoEncontradaException("Não foi possível buscar o endereço, pois ele não existe no banco de dados"));
    }

    public Page<EnderecoResponse> buscarTodosEnderecos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return enderecoRepository.findAll(pageable)
                .map(this.enderecoMapper::toResponse);
    }

    public void removerEndereco(Long id) {
        this.pessoaRepository.findById(id).ifPresentOrElse(
                endereco -> {
                    this.enderecoRepository.deleteById(id);
                },
                () -> {
                    throw new EnderecoNaoEncontradaException("Não foi possível remover o endereço, pois ele não existe no banco de dados");
                }
        );
    }

    public void atualizarEndereco(Long id, EnderecoRequest enderecoRequest) {
        this.enderecoRepository.findById(id).ifPresentOrElse(
                endereco -> {
                    endereco.setLogradouro(enderecoRequest.logradouro());
                    endereco.setCep(enderecoRequest.cep());
                    endereco.setNumero(enderecoRequest.numero());
                    endereco.setCidade(enderecoRequest.cidade());
                    endereco.setEstado(enderecoRequest.estado());
                    this.enderecoRepository.save(endereco);
                },
                () -> {
                    throw new EnderecoNaoEncontradaException("Não foi possível atualizar o endereço, pois ele não existe no banco de dados");
                }
        );
    }

}

