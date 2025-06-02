# Sistema de Compra e Venda de Veículos 🚗💨

Este projeto é um sistema web para a compra e venda de veículos, desenvolvido como parte da disciplina de Desenvolvimento de Software para a Web 1. Ele permite que clientes visualizem veículos, façam propostas, e que lojas gerenciem seus veículos e propostas. O sistema também inclui funcionalidades administrativas para gerenciamento de clientes e lojas.

---

## ✨ Funcionalidades Principais

O sistema atende aos seguintes requisitos:

* **R1:** CRUD de clientes (requer login de administrador)[cite: 3].
* **R2:** CRUD de lojas (requer login de administrador)[cite: 4].
* **R3:** Cadastro de veículo para venda (requer login da loja)[cite: 4, 5].
    * Detalhes do veículo: CNPJ da loja, placa, modelo, chassi, ano, quilometragem, descrição, valor e até 10 fotos[cite: 6].
* **R4:** Listagem de todos os veículos em uma única página (não requer login), com filtro por modelo[cite: 7, 8].
* **R5:** Proposta de compra de veículo (requer login do cliente)[cite: 9].
    * O cliente informa valor da proposta e condições de pagamento[cite: 11].
    * A data da proposta é registrada[cite: 12].
    * Restrição de uma proposta em aberto por cliente para cada veículo[cite: 13].
* **R6:** Listagem de todos os veículos de uma loja (requer login da loja)[cite: 14, 15].
* **R7:** Listagem de todas as propostas de compra de um cliente com seus status (ABERTO, NÃO ACEITO, ACEITO) (requer login do cliente)[cite: 16, 17, 18, 19, 20].
* **R8:** Análise e atualização do status da proposta pela loja (NÃO ACEITO ou ACEITO) (requer login da loja)[cite: 21].
    * Cliente é informado por e-mail sobre a decisão[cite: 22].
    * Para "NÃO ACEITO", a loja pode enviar uma contraproposta opcional[cite: 23].
    * Para "ACEITO", a loja informa horário e link para reunião por videoconferência[cite: 24].
* **R9:** Internacionalização (Português + outro idioma)[cite: 25].
* **R10:** Validação de todas as informações cadastradas/editadas e tratamento de erros amigável com logging no console[cite: 26, 27].

---

## 🛠️ Tecnologias Utilizadas

O projeto segue a arquitetura Modelo-Visão-Controlador (MVC) [cite: 28] e utiliza as seguintes tecnologias:

### Lado Servidor:
* **Spring MVC** [cite: 28]
* **Spring Data JPA** [cite: 28]
* **Spring Security** [cite: 28]
* **Thymeleaf** (para renderização de templates no servidor) [cite: 28]

### Lado Cliente:
* **HTML5**
* **CSS3** [cite: 28]
* **JavaScript** [cite: 28]
* **Bootstrap 5** (para estilização e componentes responsivos)

### Ambiente de Desenvolvimento e Build:
* **Maven** (para compilação e deployment obrigatório) [cite: 28]
* **Git & GitHub** (para controle de versão, hospedagem obrigatória no GitHub preferencialmente) [cite: 29]

### Banco de Dados:
* O login e senha do administrador devem ser populados no banco de dados durante a inicialização do sistema[cite: 30]. (O tipo de banco de dados específico não foi mencionado, mas Spring Data JPA permite flexibilidade).

---

## 📋 Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:
* Java Development Kit (JDK) (versão compatível com o Spring Boot usado)
* Apache Maven
* Git
* Seu IDE de preferência (ex: IntelliJ IDEA, Eclipse, VS Code com extensões Java)
* Um sistema de gerenciamento de banco de dados (ex: PostgreSQL, MySQL, H2 para desenvolvimento)

---

## 🚀 Configuração e Execução

1.  **Clone o repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO_GITHUB]
    cd nome-do-projeto
    ```

2.  **Configuração do Banco de Dados:**
    * Acesse o arquivo `src/main/resources/application.properties` (ou `application.yml`).
    * Configure as propriedades do datasource do Spring (URL do banco, usuário, senha) de acordo com o banco de dados que você escolheu.
        ```properties
        # Exemplo para H2 (banco em memória)
        # spring.datasource.url=jdbc:h2:mem:testdb
        # spring.datasource.driverClassName=org.h2.Driver
        # spring.datasource.username=sa
        # spring.datasource.password=password
        # spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
        # spring.h2.console.enabled=true # Para acessar o console do H2 em /h2-console

        # Exemplo para PostgreSQL
        # spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
        # spring.datasource.username=seu_usuario_pg
        # spring.datasource.password=sua_senha_pg
        # spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
        ```
    * Lembre-se que o login e senha do administrador devem ser populados durante a inicialização[cite: 30]. Isso pode ser feito via um arquivo `data.sql` em `src/main/resources` ou programaticamente.

3.  **Compile e Execute o Projeto com Maven:**
    * Abra um terminal na raiz do projeto.
    * Execute o comando Maven para iniciar a aplicação Spring Boot:
        ```bash
        mvn spring-boot:run
        ```
    * A aplicação estará acessível em `http://localhost:8080` (ou a porta configurada).

4.  **Acesso ao Sistema:**
    * **Página inicial (listagem de veículos):** `http://localhost:8080/`
    * **Login do Administrador:** As credenciais de admin são populadas na inicialização[cite: 30]. A página de login pode ser `/admin/login` ou similar, dependendo da configuração do Spring Security.

---

## 📁 Estrutura do Projeto (Simplificada)
