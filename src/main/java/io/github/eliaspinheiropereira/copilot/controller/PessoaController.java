package io.github.eliaspinheiropereira.copilot.controller;

import io.github.eliaspinheiropereira.copilot.dto.request.PessoaRequest;
import io.github.eliaspinheiropereira.copilot.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

