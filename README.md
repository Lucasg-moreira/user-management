# User Management System

Aplicação feita para o teste técnico do JC, com as tecnologias:
- Java 24
- Spring Boot
- Keycloak
- H2
- Next.js
- Docker e Docker Compose

## Requisitos do Sistema
- JDK 24
- Node.js 18+ (para desenvolvimento frontend)
- Docker e Docker Compose
- Maven
- IDE recomendada: IntelliJ IDEA

## Configuração do Ambiente
1. Clone o repositório
2. Certifique-se de que todas as dependências estão instaladas

## Estrutura do Projeto
```
.
├── frontend/          # Aplicação Next.js
└── backend/          # API Spring Boot
```

## Endpoints da API
### Autenticação
- POST /api/auth/login
- POST /api/auth/logout
- GET /api/auth/me

### Usuários
- GET /api/users
- GET /api/users/{id}
- POST /api/users
- PUT /api/users/{id}
- DELETE /api/users/{id}

## Testes
Para executar os testes:
```bash
# Backend
mvn test

# Frontend
cd frontend
npm test
```

## Troubleshooting
### Problemas Comuns
1. **Erro de conexão com Keycloak**
   - Verifique se o container do Keycloak está rodando
   - Confirme as credenciais de acesso

2. **Erro de compilação**
   - Verifique se está usando JDK 24
   - Execute `mvn clean` antes de compilar

3. **Problemas com Docker**
   - Verifique se o Docker está rodando
   - Execute `docker system prune` para limpar recursos não utilizados

## Tomadas de Decisão
A API é estruturada com Clean Architecture e Vertical Slices, um padrão com organizações por features, 
facilitando a manutenibilidade do projeto. Poderia usar uma abordagem com CQRS, mas, pelo tamanho do projeto, e como 
o tempo está em jogo também, optei por uma arquitetura mais simples e isso vai se estender para a maioria das outras decisões.

Usei Keycloak porque atualmente, acredito fortemente ser a forma mais simples e rápida de ter OAuth2 com JWT. Visando segurança do token,
seto o token em um cookie com httpOnly, visando maior segurança.

Docker para a criação de containers e Docker Compose para gerenciar múltiplos containers.

## Autenticação
Há duas roles no Keycloak:
- **ADMIN**: Contém permissão de leitura e escrita
- **USER**: Contém apenas permissão de leitura

### Credenciais Pré-configuradas
**Usuário Admin:**
- Usuário: `admin`
- Senha: `admin`

**Usuário Normal:**
- Usuário: `user`
- Senha: `admin`

## Executando o Projeto
### Via IDE (Recomendado)
1. Abra o projeto no IntelliJ IDEA
2. Execute os seguintes comandos:
   ```bash
   mvn clean package
   mvnw spring-boot:run
   ```

### Via Docker
É necessário ter o Docker instalado na sua máquina.

1. Execute o seguinte comando:
   ```bash
   docker compose up
   ```

   Este comando já irá buscar as imagens no repositório DockerHub. Entretanto, caso as imagens não estejam mais disponíveis,
   você pode seguir os seguintes passos para criar as imagens:

   ```bash
   cd frontend
   docker build -t vohail/frontend-user:1.0 .
   cd ../backend
   docker build -t vohail/api-user:1.0 .
   docker compose up
   ```

Coleção do postman estará disponível também no projeto.