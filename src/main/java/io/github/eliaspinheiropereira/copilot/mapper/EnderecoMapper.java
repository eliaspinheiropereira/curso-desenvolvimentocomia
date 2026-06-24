package io.github.eliaspinheiropereira.copilot.mapper;

import io.github.eliaspinheiropereira.copilot.dto.request.EnderecoRequest;
import io.github.eliaspinheiropereira.copilot.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(target = "pessoa", ignore = true)

    Endereco toEntity(EnderecoRequest enderecoRequest);
    EnderecoRequest toRequest(Endereco endereco);
}

