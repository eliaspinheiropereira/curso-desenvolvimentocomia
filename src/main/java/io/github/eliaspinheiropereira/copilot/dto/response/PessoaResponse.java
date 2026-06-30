package io.github.eliaspinheiropereira.copilot.dto.response;

import java.util.List;

public record PessoaResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        List<EnderecoResponse> enderecos
) {
}

