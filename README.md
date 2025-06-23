# Sistema de Compra e Venda de Veículos 🚗💨

Este projeto é um sistema web para a compra e venda de veículos, desenvolvido como parte da disciplina de Desenvolvimento de Software para a Web 1. Ele permite que clientes visualizem veículos, façam propostas, e que lojas gerenciem seus veículos e propostas. O sistema também inclui funcionalidades administrativas para gerenciamento de clientes e lojas.

---

## ✨ Funcionalidades Principais

O sistema atende aos seguintes requisitos:

* **R1:** CRUD de clientes (requer login de administrador).
* **R2:** CRUD de lojas (requer login de administrador).
* **R3:** Cadastro de veículo para venda (requer login da loja via e-mail + senha).
    * Detalhes do veículo: CNPJ da loja, placa, modelo, chassi, ano, quilometragem, descrição, valor e até 10 fotos.
* **R4:** Listagem de todos os veículos em uma única página (não requer login), com filtro por modelo.
* **R5:** Proposta de compra de veículo (requer login do cliente via e-mail + senha).
    * O cliente informa valor da proposta e as condições de pagamento.
    * A data da proposta (data atual) é registrada no sistema.
    * Restrição de uma proposta em aberto por cliente para cada veículo (referente ao requisito R7).
* **R6:** Listagem de todos os veículos de uma loja (requer login da loja via e-mail + senha).
* **R7:** Listagem de todas as propostas de compra de um cliente com seus respectivos status (ABERTO, NÃO ACEITO, ACEITO) (requer login do cliente via e-mail + senha).
    * Status ABERTO: indica análise pela loja.
    * Status NÃO ACEITO: indica que a loja não aceitou a proposta.
    * Status ACEITO: indica que a loja aceitou a proposta.
* **R8:** Análise e atualização do status da proposta pela loja (NÃO ACEITO ou ACEITO) (requer login da loja via e-mail + senha).
    * Cliente é informado por e-mail sobre a decisão.
    * Para "NÃO ACEITO", a loja pode enviar uma contraproposta opcional (valor e condições de pagamento) no corpo da mensagem.
    * Para "ACEITO", a loja informa horário para reunião (via videoconferência) e o link da videoconferência no corpo da mensagem.
* **R9:** Internacionalização (Português + outro idioma de sua escolha).
* **R10:** Validação de todas as informações (campos nos formulários) cadastradas e/ou editadas (tamanho, formato, etc.). Tratamento de todos os erros possíveis (cadastros duplicados, problemas técnicos, etc.) mostrando uma página de erros amigável ao usuário e registrando o erro no console.

---

## 🛠️ Tecnologias Utilizadas

O projeto segue a arquitetura Modelo-Visão-Controlador (MVC) e utiliza as seguintes tecnologias:

### Lado Servidor:
* **Spring MVC**
* **Spring Data JPA**
* **Spring Boot DevTools**
* **Spring Security** 
* **Thymeleaf**

### Lado Cliente:
* **HTML5**
* **CSS3** 
* **JavaScript** 
* **Tailwind**

### Ambiente de Desenvolvimento e Build:
* **Maven** (para compilação e deployment obrigatório)
* **Git & GitHub** (para controle de versão, hospedagem obrigatória em um repositório, preferencialmente GitHub)

### Banco de Dados:
* O login e senha do administrador devem ser populados no banco de dados durante a inicialização do sistema.
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
    git clone [URL_DO_PROJETO]
    cd nome-do-projeto
    ```

2.  **Configuração e Inicialização do Banco de Dados com Docker:**
    * Crie um arquivo `docker-compose.yml` na raiz do projeto para definir o serviço do seu banco de dados (ex: PostgreSQL).
        ```yaml
        # Exemplo de docker-compose.yml para PostgreSQL
        version: '3.8'
        services:
          Sistema-cvv-db:
            image: 'postgres:16.0-alpine3.18' #Ou a versão de sua preferência
            environment:
              POSTGRES_DB: db
              POSTGRES_USER: root
              POSTGRES_PASSWORD: root
            ports:
              - "5432:5432"
        ```
    * Suba o container do banco de dados:
        ```bash
        docker-compose up -d
        ```
    * Acesse o arquivo `src/main/resources/application.properties` (ou `application.yml`).
    * Configure as propriedades do datasource do Spring para conectar ao banco de dados Dockerizado. Estas devem corresponder às variáveis de ambiente definidas no `docker-compose.yml`.
        ```properties
        # Exemplo para PostgreSQL rodando via Docker (conforme docker-compose.yml acima)
        spring.datasource.url= jdbc:postgresql://localhost:5432/db
        spring.datasource.username= root
        spring.datasource.password= root
        spring.jpa.hibernate.ddl-auto= update
        spring.jpa.show-sql= true
        ```
    * Lembre-se que o login e senha do administrador devem ser populados durante a inicialização do sistema. Isso pode ser feito via um arquivo `data.sql` em `src/main/resources` ou programaticamente usando um `CommandLineRunner` ou `ApplicationRunner` bean.

3.  **Compile e Execute o Projeto com Maven:**
    * Abra um terminal na raiz do projeto.
    * Execute o comando Maven para iniciar a aplicação Spring Boot:
        ```bash
        mvn spring-boot:run
        ```
         ```bash
        mvn spring-boot:run -Pseed
        ```
         Esse comando executará a seed do sistema onde conterá dados essenciais como admin, cliente, loja, etc  
         Recomendado o uso desse comando na primeira vez usando o sistema
         email: admin@example.com
         password: 123
    * A aplicação estará acessível em `http://localhost:8080` (ou a porta configurada).

4.  **Acesso ao Sistema:**
    * **Página inicial (listagem de veículos):** `http://localhost:8080/`
    * **Login do Administrador:** As credenciais de admin são populadas na inicialização. A página de login pode ser `/admin/login` ou similar, dependendo da configuração do Spring Security.

---
