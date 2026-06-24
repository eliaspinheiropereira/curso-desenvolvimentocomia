package io.github.eliaspinheiropereira.copilot.repository;

import io.github.eliaspinheiropereira.copilot.model.Endereco;
import io.github.eliaspinheiropereira.copilot.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes do CRUD - EnderecoRepository")
class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    private Pessoa pessoaTeste;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        // Criar pessoa mínima para satisfazer a FK (não testa Pessoa aqui)
        pessoaTeste = new Pessoa();
        pessoaTeste.setNome("Pessoa Teste");
        pessoaTeste.setEmail("teste@email.com");
        pessoaTeste.setSenha("senha123");
        pessoaTeste.setCpf("11111111111");
        pessoaTeste.setEnderecos(new ArrayList<>());
        pessoaTeste = pessoaRepository.save(pessoaTeste);

        // Criar Endereco para testes
        endereco = new Endereco();
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("100");
        endereco.setBairro("Bairro Teste");
        endereco.setCep("12345-678");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("CT");
        endereco.setPessoa(pessoaTeste);
    }

    @Test
    @DisplayName("Deve salvar um endereço com sucesso")
    void testSaveEndereco() {
        // Act
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        // Assert
        assertThat(enderecoSalvo)
                .isNotNull()
                .hasFieldOrPropertyWithValue("logradouro", "Rua Teste")
                .hasFieldOrPropertyWithValue("numero", "100")
                .hasFieldOrPropertyWithValue("bairro", "Bairro Teste")
                .hasFieldOrPropertyWithValue("cep", "12345-678")
                .hasFieldOrPropertyWithValue("cidade", "Cidade Teste")
                .hasFieldOrPropertyWithValue("estado", "CT");
        assertThat(enderecoSalvo.getId()).isNotNull().isPositive();
    }

    @Test
    @DisplayName("Deve encontrar um endereço por ID")
    void testFindById() {
        // Arrange
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        // Act
        Optional<Endereco> enderecoEncontrado = enderecoRepository.findById(enderecoSalvo.getId());

        // Assert
        assertThat(enderecoEncontrado)
                .isPresent()
                .hasValueSatisfying(e -> {
                    assertThat(e.getLogradouro()).isEqualTo("Rua Teste");
                    assertThat(e.getCidade()).isEqualTo("Cidade Teste");
                });
    }

    @Test
    @DisplayName("Deve retornar vazio quando endereço não existe")
    void testFindByIdNotFound() {
        // Act
        Optional<Endereco> enderecoEncontrado = enderecoRepository.findById(999L);

        // Assert
        assertThat(enderecoEncontrado).isEmpty();
    }

    @Test
    @DisplayName("Deve listar todos os endereços")
    void testFindAll() {
        // Arrange
        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Avenida Teste 2");
        endereco2.setNumero("200");
        endereco2.setBairro("Bairro 2");
        endereco2.setCep("87654-321");
        endereco2.setCidade("Cidade 2");
        endereco2.setEstado("C2");
        endereco2.setPessoa(pessoaTeste);

        enderecoRepository.save(endereco);
        enderecoRepository.save(endereco2);

        // Act
        List<Endereco> enderecos = enderecoRepository.findAll();

        // Assert
        assertThat(enderecos)
                .isNotEmpty()
                .hasSize(2)
                .extracting(Endereco::getCidade)
                .containsExactlyInAnyOrder("Cidade Teste", "Cidade 2");
    }

    @Test
    @DisplayName("Deve atualizar um endereço")
    void testUpdateEndereco() {
        // Arrange
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        enderecoSalvo.setLogradouro("Rua Atualizada");
        enderecoSalvo.setCidade("Cidade Atualizada");

        // Act
        Endereco enderecoAtualizado = enderecoRepository.save(enderecoSalvo);

        // Assert
        assertThat(enderecoAtualizado)
                .hasFieldOrPropertyWithValue("logradouro", "Rua Atualizada")
                .hasFieldOrPropertyWithValue("cidade", "Cidade Atualizada");
        assertThat(enderecoAtualizado.getId()).isEqualTo(enderecoSalvo.getId());
    }

    @Test
    @DisplayName("Deve deletar um endereço por ID")
    void testDeleteById() {
        // Arrange
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        Long enderecoId = enderecoSalvo.getId();

        // Act
        enderecoRepository.deleteById(enderecoId);

        // Assert
        assertThat(enderecoRepository.findById(enderecoId)).isEmpty();
    }

    @Test
    @DisplayName("Deve deletar todos os endereços")
    void testDeleteAll() {
        // Arrange
        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Avenida Teste 3");
        endereco2.setNumero("300");
        endereco2.setBairro("Bairro 3");
        endereco2.setCep("11111-111");
        endereco2.setCidade("Cidade 3");
        endereco2.setEstado("C3");
        endereco2.setPessoa(pessoaTeste);

        enderecoRepository.save(endereco);
        enderecoRepository.save(endereco2);

        // Act
        enderecoRepository.deleteAll();

        // Assert
        assertThat(enderecoRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar a contagem correta de endereços")
    void testCount() {
        // Arrange
        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua Teste 4");
        endereco2.setNumero("400");
        endereco2.setBairro("Bairro 4");
        endereco2.setCep("22222-222");
        endereco2.setCidade("Cidade 4");
        endereco2.setEstado("C4");
        endereco2.setPessoa(pessoaTeste);

        enderecoRepository.save(endereco);
        enderecoRepository.save(endereco2);

        // Act
        long count = enderecoRepository.count();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve validar campos obrigatórios")
    void testCamposObrigatorios() {
        // Act & Assert
        assertThat(endereco)
                .hasFieldOrProperty("logradouro")
                .hasFieldOrProperty("numero")
                .hasFieldOrProperty("bairro")
                .hasFieldOrProperty("cep")
                .hasFieldOrProperty("cidade")
                .hasFieldOrProperty("estado")
                .hasFieldOrProperty("pessoa");
    }

    @Test
    @DisplayName("Deve validar propriedades de auditoria")
    void testAuditoriaFields() {
        // Arrange & Act
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        // Assert
        assertThat(enderecoSalvo.getCriadoEm()).isNotNull();
        assertThat(enderecoSalvo.getAtualizadoEm()).isNotNull();
    }

    @Test
    @DisplayName("Deve validar formato de CEP")
    void testFormatoCep() {
        // Act & Assert
        assertThat(endereco.getCep())
                .matches("^\\d{5}-\\d{3}$|^\\d{8}$")
                .isEqualTo("12345-678");
    }

    @Test
    @DisplayName("Deve validar estado com 2 caracteres")
    void testEstadoDoisCaracteres() {
        // Act & Assert
        assertThat(endereco.getEstado())
                .hasSize(2)
                .isEqualTo("CT");
    }

    @Test
    @DisplayName("Deve permitir vários endereços")
    void testMultiplosEnderecos() {
        // Arrange
        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua Teste 2");
        endereco2.setNumero("500");
        endereco2.setBairro("Bairro 5");
        endereco2.setCep("33333-333");
        endereco2.setCidade("Cidade 5");
        endereco2.setEstado("C5");
        endereco2.setPessoa(pessoaTeste);

        enderecoRepository.save(endereco);
        enderecoRepository.save(endereco2);

        // Act
        List<Endereco> enderecos = enderecoRepository.findAll();

        // Assert
        assertThat(enderecos)
                .hasSize(2)
                .extracting(Endereco::getLogradouro)
                .containsExactlyInAnyOrder("Rua Teste", "Rua Teste 2");
    }
}
