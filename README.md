# 👤 Copilot - API de Gerenciamento de Pessoas e Endereços

Uma API REST desenvolvida com **Spring Boot 3.5.15**, **Java 21**, **MapStruct**, **Lombok** e **AssertJ** para gerenciar pessoas e seus endereços.

## 📋 Índice

- [Tecnologias](#tecnologias)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configuração](#configuração)
- [Como Rodar](#como-rodar)
- [Endpoints](#endpoints)
- [Exemplos de Uso](#exemplos-de-uso)
- [Validações](#validações)

## 🛠 Tecnologias

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| Spring Boot | 3.5.15 | Framework principal |
| Java | 21 | Linguagem de programação |
| Maven | - | Gerenciador de dependências |
| H2 Database | 2.3.232 | Banco de dados em memória |
| Lombok | 1.18.46 | Gerador de boilerplate |
| MapStruct | 1.5.5.Final | Mapeador de objetos |
| AssertJ | 3.27.7 | Framework de testes |
| Hibernate | 6.6.53.Final | ORM |
| Validation | - | Jakarta Validation |

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/io/github/eliaspinheiropereira/copilot/
│   │   ├── model/              # Entidades JPA
│   │   │   ├── Pessoa.java     # Entidade de Pessoa
│   │   │   └── Endereco.java   # Entidade de Endereço
│   │   ├── repository/         # Repositórios Spring Data JPA
│   │   │   ├── PessoaRepository.java
│   │   │   └── EnderecoRepository.java
│   │   ├── service/            # Lógica de negócio
│   │   │   └── PessoaService.java
│   │   ├── controller/         # REST Controllers
│   │   │   └── PessoaController.java
│   │   ├── mapper/             # MapStruct Mappers
│   │   │   ├── PessoaMapper.java
│   │   │   └── EnderecoMapper.java
│   │   ├── dto/                # Data Transfer Objects
│   │   │   └── request/
│   │   │       ├── PessoaRequest.java
│   │   │       └── EnderecoRequest.java
│   │   ├── validator/          # Validadores customizados
│   │   │   └── PessoaValidator.java
│   │   ├── exceptions/         # Exceções customizadas
│   │   └── CopilotApplication.java
│   └── resources/
│       └── application.yaml    # Configurações da aplicação
└── test/
    └── java/io/github/eliaspinheiropereira/copilot/
        └── repository/
            ├── PessoaRepositoryTest.java
            └── EnderecoRepositoryTest.java
```

## ⚙️ Configuração

### application.yaml

O projeto usa **H2 Database** em memória com configurações pré-definidas:

```yaml
spring:
  application:
    name: copilot
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password: 
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

**H2 Console:** http://localhost:8080/h2-console

## 🚀 Como Rodar

### Pré-requisitos
- Java 21+
- Maven 3.6+

### Passos

1. **Clonar o repositório**
```bash
cd /home/eliasppereira/dev/backend/copilot/copilot
```

2. **Compilar o projeto**
```bash
mvn clean compile
```

3. **Executar a aplicação**
```bash
mvn spring-boot:run
```

4. **A aplicação estará disponível em:**
```
http://localhost:8080
```

## 🔌 Endpoints

### 1. Criar Pessoa com Endereços

**POST** `/api/pessoas`

Cria uma nova pessoa com um ou múltiplos endereços.

**Exemplo de Requisição:**

```bash
curl -X POST http://localhost:8080/api/pessoas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Silva Santos",
    "email": "maria.silva@email.com",
    "senha": "SenhaForte123",
    "cpf": "12345678901",
    "enderecos": [
      {
        "logradouro": "Rua das Flores",
        "numero": "456",
        "bairro": "Jardim Paulista",
        "cep": "01234-567",
        "cidade": "São Paulo",
        "estado": "SP"
      }
    ]
  }'
```

**Respostas:**

- **201 Created** - Pessoa salva com sucesso
```
HTTP/1.1 201 Created
```

- **400 Bad Request** - Validação falhou
```json
{
  "error": "Nome deve ter entre 3 e 100 caracteres"
}
```

---

## 📝 Exemplos de Uso

### Via Postman

1. **Abra o Postman**
2. **Crie uma nova requisição POST**
3. **URL:** `http://localhost:8080/api/pessoas`
4. **Headers:**
   - `Content-Type: application/json`
5. **Body (raw):**
```json
{
  "nome": "Carlos Alberto",
  "email": "carlos@email.com",
  "senha": "Carlos@123",
  "cpf": "55555555555",
  "enderecos": [
    {
      "logradouro": "Rua Principal",
      "numero": "123",
      "bairro": "Centro",
      "cep": "12345-678",
      "cidade": "Belo Horizonte",
      "estado": "MG"
    }
  ]
}
```

6. **Clique em Send**

---

## ✅ Validações

### Pessoa

| Campo | Regras |
|-------|--------|
| **Nome** | Obrigatório, 3-100 caracteres |
| **Email** | Obrigatório, formato válido, único no banco |
| **Senha** | Obrigatória, 6-100 caracteres |
| **CPF** | Obrigatório, exatamente 11 caracteres, único no banco |
| **Endereços** | Opcional, lista validada em cascata |

### Endereço

| Campo | Regras |
|-------|--------|
| **Logradouro** | Obrigatório, 3-100 caracteres |
| **Número** | Obrigatório, 1-10 caracteres |
| **Bairro** | Obrigatório, 3-50 caracteres |
| **CEP** | Obrigatório, formato XXXXX-XXX ou XXXXXXXX |
| **Cidade** | Obrigatória, 3-100 caracteres |
| **Estado** | Obrigatório, exatamente 2 caracteres (ex: SP, RJ, MG) |

### Exemplo de Erro de Validação

```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "timestamp": "2026-06-23T19:30:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validação falhou",
  "details": {
    "email": "Email deve ser válido",
    "cpf": "CPF deve ter exatamente 11 caracteres",
    "enderecos[0].cep": "CEP deve estar no formato XXXXX-XXX ou XXXXXXXX"
  }
}
```

---

## 🏗 Architecture

### Fluxo de Requisição

```
Cliente (POST /api/pessoas)
    ↓
PessoaController
    ↓
@Valid (Validação de Bean)
    ↓
PessoaValidator (Validação customizada)
    ↓
PessoaService.salvarPessoa()
    ↓
PessoaMapper.toEntity() (DTO → Entidade)
    ↓
@AfterMapping (Define pessoa em cada endereco)
    ↓
PessoaRepository.save()
    ↓
Banco de Dados (H2)
    ↓
HTTP 201 Created
```

### Relacionamento Pessoa-Endereco

- **Tipo:** One-to-Many (1:N)
- **Cascade:** ALL (operações em cascata)
- **Orphan Removal:** true (remove endereços orfãos)
- **Fetch:** LAZY (carregamento preguiçoso)
- **Bidirecional:** Sim (Pessoa → Endereco, Endereco → Pessoa)

---

## 🔐 Auditoria

Todas as entidades possuem campos de auditoria automáticos:

- **criadoEm** - Data de criação (não editável)
- **atualizadoEm** - Data de última atualização

Preenchidos automaticamente pelo Spring Data JPA com `@CreatedDate` e `@LastModifiedDate`.

---

## 📚 Stack Spring Boot

```xml
<!-- Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Web (REST) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- MapStruct -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>
```

---

## 💡 Padrões de Design Utilizados

1. **Data Transfer Object (DTO)** - Transporte de dados entre camadas
2. **Repository Pattern** - Abstração do acesso a dados
3. **Service Layer** - Lógica de negócio centralizada
4. **Mapper Pattern** - Conversão entre entidades e DTOs
5. **Validator Pattern** - Validações customizadas
6. **Entity Listener** - Auditoria automática
7. **Dependency Injection** - Injeção de dependências do Spring

---

## 📞 Autor

**Elias Pinheiro Pereira**

---

## 📄 Licença

Este projeto está sob a licença MIT.

---

## 🚀 Próximos Passos

- [ ] Implementar endpoints GET (buscar pessoa por ID, listar todas)
- [ ] Implementar endpoints PUT (atualizar pessoa)
- [ ] Implementar endpoints DELETE (deletar pessoa)
- [ ] Adicionar paginação e filtros
- [ ] Implementar autenticação e autorização
- [ ] Adicionar documentação Swagger/OpenAPI
- [ ] Criar testes de integração
- [ ] Deploy em produção
