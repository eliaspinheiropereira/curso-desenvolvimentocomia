package io.github.eliaspinheiropereira.copilot.controller;

import io.github.eliaspinheiropereira.copilot.dto.request.PessoaRequest;
import io.github.eliaspinheiropereira.copilot.dto.response.PessoaResponse;
import io.github.eliaspinheiropereira.copilot.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
@Slf4j
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Void> criarPessoa(
            @Valid @RequestBody PessoaRequest pessoaRequest
    ) {
        log.info("POST -> /api/pessoas {}", pessoaRequest.toString());
        pessoaService.salvarPessoa(pessoaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> buscarPessoaPorId(
            @PathVariable Long id
    ) {
        log.info("GET -> /api/pessoas/{}", id);
        PessoaResponse pessoa = pessoaService.buscarPessoaPorId(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> buscarTodasPessoas(
            @RequestParam int page,
            @RequestParam int size
    ) {
        log.info("GET -> /api/pessoas?page={}&size={}", page, size);
        Page<PessoaResponse> pessoas = pessoaService.buscarTodasPessoas(page, size);
        return ResponseEntity.ok(pessoas.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarPessoa(
            @PathVariable Long id,
            @Valid @RequestBody PessoaRequest pessoaRequest
    ) {
        log.info("PUT -> /api/pessoas/{} {}", id, pessoaRequest.toString());
        pessoaService.atualizarPessoa(id, pessoaRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPessoa(
            @PathVariable Long id
    ) {
        log.info("DELETE -> /api/pessoas/{}", id);
        pessoaService.removerPessoa(id);
        return ResponseEntity.noContent().build();
    }
}

