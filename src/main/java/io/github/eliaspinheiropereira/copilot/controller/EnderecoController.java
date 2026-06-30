package io.github.eliaspinheiropereira.copilot.controller;

import io.github.eliaspinheiropereira.copilot.dto.request.EnderecoRequest;
import io.github.eliaspinheiropereira.copilot.dto.response.EnderecoResponse;
import io.github.eliaspinheiropereira.copilot.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
@RequiredArgsConstructor
@Slf4j
public class EnderecoController {

    private final EnderecoService enderecoService;


    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> buscarEnderecoPorId(
            @PathVariable Long id
    ) {
        log.info("GET -> /api/enderecos/{}", id);
        EnderecoResponse endereco = enderecoService.buscarEnderecoPorId(id);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoResponse>> buscarTodosEnderecos(
            @RequestParam int page,
            @RequestParam int size
    ) {
        log.info("GET -> /api/enderecos?page={}&size={}", page, size);
        Page<EnderecoResponse> enderecos = enderecoService.buscarTodosEnderecos(page, size);
        return ResponseEntity.ok(enderecos.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarEndereco(
            @PathVariable Long id,
            @Valid @RequestBody EnderecoRequest enderecoRequest
    ) {
        log.info("PUT -> /api/enderecos/{} {}", id, enderecoRequest.toString());
        enderecoService.atualizarEndereco(id, enderecoRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEndereco(
            @PathVariable Long id
    ) {
        log.info("DELETE -> /api/enderecos/{}", id);
        enderecoService.removerEndereco(id);
        return ResponseEntity.noContent().build();
    }
}

