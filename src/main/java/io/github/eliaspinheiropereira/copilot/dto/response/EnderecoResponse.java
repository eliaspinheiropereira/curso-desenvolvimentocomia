package io.github.eliaspinheiropereira.copilot.dto.response;

public record EnderecoResponse(
        Long id,
        String logradouro,
        String numero,
        String bairro,
        String cep,
        String cidade,
        String estado
) {
}

