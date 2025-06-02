# Sistema de Compra e Venda de Veículos 🚗💨

Este projeto é um sistema web para a compra e venda de veículos, desenvolvido como parte da disciplina de Desenvolvimento de Software para a Web 1. Ele permite que clientes visualizem veículos, façam propostas, e que lojas gerenciem seus veículos e propostas. O sistema também inclui funcionalidades administrativas para gerenciamento de clientes e lojas.

---

## ✨ Funcionalidades Principais

O sistema atende aos seguintes requisitos:

* **R1:** CRUD de clientes (requer login de administrador)[cite: 3].
* **R2:** CRUD de lojas (requer login de administrador)[cite: 4].
* **R3:** Cadastro de veículo para venda (requer login da loja via e-mail + senha)[cite: 3, 5].
    * Detalhes do veículo: CNPJ da loja, placa, modelo, chassi, ano, quilometragem, descrição, valor e até 10 fotos[cite: 6].
* **R4:** Listagem de todos os veículos em uma única página (não requer login)[cite: 7], com filtro por modelo[cite: 8].
* **R5:** Proposta de compra de veículo (requer login do cliente via e-mail + senha)[cite: 9].
    * O cliente informa valor da proposta e as condições de pagamento[cite: 11].
    * A data da proposta (data atual) é registrada no sistema[cite: 12].
    * Restrição de uma proposta em aberto por cliente para cada veículo (referente ao requisito R7)[cite: 13].
* **R6:** Listagem de todos os veículos de uma loja (requer login da loja via e-mail + senha)[cite: 14, 15].
* **R7:** Listagem de todas as propostas de compra de um cliente com seus respectivos status (ABERTO, NÃO ACEITO, ACEITO) (requer login do cliente via e-mail + senha)[cite: 16, 17].
    * Status ABERTO: indica análise pela loja[cite: 18].
    * Status NÃO ACEITO: indica que a loja não aceitou a proposta[cite: 19].
    * Status ACEITO: indica que a loja aceitou a proposta[cite: 20].
* **R8:** Análise e atualização do status da proposta pela loja (NÃO ACEITO ou ACEITO) (requer login da loja via e-mail + senha)[cite: 21].
    * Cliente é informado por e-mail sobre a decisão[cite: 22].
    * Para "NÃO ACEITO", a loja pode enviar uma contraproposta opcional (valor e condições de pagamento) no corpo da mensagem[cite: 23].
    * Para "ACEITO", a loja informa horário para reunião (via videoconferência) e o link da videoconferência no corpo da mensagem[cite: 24].
* **R9:** Internacionalização (Português + outro idioma de sua escolha)[cite: 25].
* **R10:** Validação de todas as informações (campos nos formulários) cadastradas e/ou editadas (tamanho, formato, etc.)[cite: 26]. Tratamento de todos os erros possíveis (cadastros duplicados, problemas técnicos, etc.) mostrando uma página de erros amigável ao usuário e registrando o erro no console[cite: 27].

---

## 🛠️ Tecnologias Utilizadas

O projeto segue a arquitetura Modelo-Visão-Controlador (MVC) [cite: 28] e utiliza as seguintes tecnologias:

### Lado Servidor:
* **Spring MVC** [cite: 28]
* **Spring Data JPA** (incluindo JPA Repositories) [cite: 28]
* **Spring Security** [cite: 28]
* **Thymeleaf** (para renderização de templates no servidor) [cite: 28]

### Lado Cliente:
* **HTML5**
* **CSS3** [cite: 28]
* **JavaScript** [cite: 28]
* **Bootstrap 5** (para estilização e componentes responsivos)

### Ambiente de Desenvolvimento e Build:
* **Maven** (para compilação e deployment obrigatório) [cite: 28]
* **Git & GitHub** (para controle de versão, hospedagem obrigatória em um repositório, preferencialmente GitHub) [cite: 29]

### Banco de Dados:
* O login e senha do administrador devem ser populados no banco de dados durante a inicialização do sistema[cite: 30].
* Gerenciado via **Docker**.

---

## 📋 Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:
* Java Development Kit (JDK) (versão compatível com o Spring Boot usado)
* Apache Maven
* Git
* **Docker** e **Docker Compose** (para gerenciamento do container do banco de dados)
* Seu IDE de preferência (ex: IntelliJ IDEA, Eclipse, VS Code com extensões Java)

---

## 🚀 Configuração e Execução

1.  **Clone o repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO_GITHUB]
    cd nome-do-projeto
    ```

2.  **Configuração e Inicialização do Banco de Dados com Docker:**
    * Crie um arquivo `docker-compose.yml` na raiz do projeto para definir o serviço do seu banco de dados (ex: PostgreSQL).
        ```yaml
        # Exemplo de docker-compose.yml para PostgreSQL
        version: '3.8'
        services:
          db:
            image: postgres:13 # Ou a versão de sua preferência
            container_name: vendecar-db # Nome do container do banco
            restart: always
            environment:
              POSTGRES_DB: vendecar_db        # Nome do banco de dados
              POSTGRES_USER: seu_usuario_db   # Usuário do banco
              POSTGRES_PASSWORD: sua_senha_db # Senha do banco
            ports:
              - "5432:5432" # Mapeia a porta do container para a porta do host
            volumes:
              - vendecar_postgres_data:/var/lib/postgresql/data

        volumes:
          vendecar_postgres_data: # Volume para persistência dos dados
        ```
    * Suba o container do banco de dados:
        ```bash
        docker-compose up -d
        ```
    * Acesse o arquivo `src/main/resources/application.properties` (ou `application.yml`).
    * Configure as propriedades do datasource do Spring para conectar ao banco de dados Dockerizado. Estas devem corresponder às variáveis de ambiente definidas no `docker-compose.yml`.
        ```properties
        # Exemplo para PostgreSQL rodando via Docker (conforme docker-compose.yml acima)
        spring.datasource.url=jdbc:postgresql://localhost:5432/vendecar_db
        spring.datasource.username=seu_usuario_db
        spring.datasource.password=sua_senha_db
        spring.jpa.hibernate.ddl-auto=update # ou 'create' para desenvolvimento inicial
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
        spring.jpa.show-sql=true # Útil para desenvolvimento
        ```
    * Lembre-se que o login e senha do administrador devem ser populados durante a inicialização do sistema[cite: 30]. Isso pode ser feito via um arquivo `data.sql` em `src/main/resources` ou programaticamente usando um `CommandLineRunner` ou `ApplicationRunner` bean.

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
