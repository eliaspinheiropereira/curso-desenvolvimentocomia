package io.github.eliaspinheiropereira.copilot.repository;

import io.github.eliaspinheiropereira.copilot.model.Endereco;
import io.github.eliaspinheiropereira.copilot.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes do CRUD - PessoaRepository")
class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private Pessoa pessoa;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setEmail("joao@email.com");
        pessoa.setSenha("senha123");
        pessoa.setCpf("12345678901");
        pessoa.setEnderecos(new java.util.ArrayList<>());

        endereco = new Endereco();
        endereco.setLogradouro("Rua A");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCep("12345-678");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
    }

    @Test
    @DisplayName("Deve salvar uma pessoa com sucesso")
    void testSavePessoa() {
        // Act
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        // Assert
        assertThat(pessoaSalva)
                .isNotNull()
                .hasFieldOrPropertyWithValue("nome", "João Silva")
                .hasFieldOrPropertyWithValue("email", "joao@email.com")
                .hasFieldOrPropertyWithValue("cpf", "12345678901");
        assertThat(pessoaSalva.getId()).isNotNull().isPositive();
    }

    @Test
    @DisplayName("Deve encontrar uma pessoa por ID")
    void testFindById() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        // Act
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findById(pessoaSalva.getId());

        // Assert
        assertThat(pessoaEncontrada)
                .isPresent()
                .hasValueSatisfying(p -> {
                    assertThat(p.getNome()).isEqualTo("João Silva");
                    assertThat(p.getEmail()).isEqualTo("joao@email.com");
                });
    }

    @Test
    @DisplayName("Deve retornar vazio quando pessoa não existe")
    void testFindByIdNotFound() {
        // Act
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findById(999L);

        // Assert
        assertThat(pessoaEncontrada).isEmpty();
    }

    @Test
    @DisplayName("Deve encontrar pessoa por email")
    void testFindByEmail() {
        // Arrange
        pessoaRepository.save(pessoa);

        // Act
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findByEmail("joao@email.com");

        // Assert
        assertThat(pessoaEncontrada)
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p.getNome()).isEqualTo("João Silva"));
    }

    @Test
    @DisplayName("Deve retornar vazio quando email não existe")
    void testFindByEmailNotFound() {
        // Act
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findByEmail("naoexiste@email.com");

        // Assert
        assertThat(pessoaEncontrada).isEmpty();
    }

    @Test
    @DisplayName("Deve encontrar pessoa por CPF")
    void testFindByCpf() {
        // Arrange
        pessoaRepository.save(pessoa);

        // Act
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findByCpf("12345678901");

        // Assert
        assertThat(pessoaEncontrada)
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p.getEmail()).isEqualTo("joao@email.com"));
    }

    @Test
    @DisplayName("Deve retornar vazio quando CPF não existe")
    void testFindByCpfNotFound() {
        // Act
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findByCpf("99999999999");

        // Assert
        assertThat(pessoaEncontrada).isEmpty();
    }

    @Test
    @DisplayName("Deve listar todas as pessoas")
    void testFindAll() {
        // Arrange
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Maria Santos");
        pessoa2.setEmail("maria@email.com");
        pessoa2.setSenha("senha456");
        pessoa2.setCpf("98765432101");

        pessoaRepository.save(pessoa);
        pessoaRepository.save(pessoa2);

        // Act
        List<Pessoa> pessoas = pessoaRepository.findAll();

        // Assert
        assertThat(pessoas)
                .isNotEmpty()
                .hasSize(2)
                .extracting(Pessoa::getNome)
                .containsExactlyInAnyOrder("João Silva", "Maria Santos");
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa")
    void testUpdatePessoa() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        pessoaSalva.setNome("João Silva Atualizado");

        // Act
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoaSalva);

        // Assert
        assertThat(pessoaAtualizada)
                .hasFieldOrPropertyWithValue("nome", "João Silva Atualizado")
                .hasFieldOrPropertyWithValue("email", "joao@email.com");
        assertThat(pessoaAtualizada.getId()).isEqualTo(pessoaSalva.getId());
    }

    @Test
    @DisplayName("Deve deletar uma pessoa por ID")
    void testDeleteById() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        Long pessoaId = pessoaSalva.getId();

        // Act
        pessoaRepository.deleteById(pessoaId);

        // Assert
        assertThat(pessoaRepository.findById(pessoaId)).isEmpty();
    }

    @Test
    @DisplayName("Deve deletar todas as pessoas")
    void testDeleteAll() {
        // Arrange
        pessoaRepository.save(pessoa);
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Maria");
        pessoa2.setEmail("maria@email.com");
        pessoa2.setSenha("123456");
        pessoa2.setCpf("11111111111");
        pessoaRepository.save(pessoa2);

        // Act
        pessoaRepository.deleteAll();

        // Assert
        assertThat(pessoaRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar a contagem correta de pessoas")
    void testCount() {
        // Arrange
        pessoaRepository.save(pessoa);
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Pedro");
        pessoa2.setEmail("pedro@email.com");
        pessoa2.setSenha("123456");
        pessoa2.setCpf("22222222222");
        pessoaRepository.save(pessoa2);

        // Act
        long count = pessoaRepository.count();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve validar que pessoa tem endereços associados")
    void testPessoaComEnderecos() {
        // Arrange
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        endereco.setPessoa(pessoaSalva);
        pessoaSalva.getEnderecos().add(endereco);
        Pessoa pessoaComEndereco = pessoaRepository.save(pessoaSalva);

        // Assert
        assertThat(pessoaComEndereco.getEnderecos())
                .isNotEmpty()
                .hasSize(1)
                .extracting(Endereco::getCidade)
                .containsExactly("São Paulo");
    }

    @Test
    @DisplayName("Deve validar propriedades de auditoria")
    void testAuditoriaFields() {
        // Arrange & Act
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        // Assert
        assertThat(pessoaSalva.getCriadoEm()).isNotNull();
        assertThat(pessoaSalva.getAtualizadoEm()).isNotNull();
    }
}

