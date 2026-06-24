package io.github.eliaspinheiropereira.copilot.mapper;

import io.github.eliaspinheiropereira.copilot.dto.request.PessoaRequest;
import io.github.eliaspinheiropereira.copilot.model.Endereco;
import io.github.eliaspinheiropereira.copilot.model.Pessoa;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface PessoaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(target = "enderecos", source = "enderecos")

    @AfterMapping
    default void setEnderecosPessoa(@MappingTarget Pessoa pessoa) {
        if (pessoa.getEnderecos() != null) {
            pessoa.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoa));
        }
    }

    Pessoa toEntity(PessoaRequest pessoaRequest);
    PessoaRequest toRequest(Pessoa pessoa);
}

