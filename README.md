<h4> EBStore </h4>

<hr>

<h3 align="center">
    <br>
    API RESTful com Spring Boot para uma loja
    <br><br>

</h3>

<hr>

## Sobre

Esta é uma API RESTful utilizando o framework Spring Boot da linguagem Java.
Além disso, tem uma interface web simples em HTML, CSS e JavaScript(Junto com a biblioteca Bootstrap 5) que consome a API.

A aplicação tem como função servir de um sistema gerencial de uma loja no setor financeiro, tendo funções de cadastro de cliente, produtos e usuários. Também contém a função de criar uma venda para um cliente e informar forma de pagamento, quantidade de produtos, uma lista dos produtos e o valor total é calculado automaticamente, após a criação é possível criar uma transação que é a parte na qual as venda vai mudar sua sitação para ter a mudança no caixa da loja.

## 🚀 Tecnologias

- Frontend:
  - [HTML 5](https://www.w3schools.com/howto/howto_make_a_website.asp/)
  - [CSS 3](https://www.w3schools.com/css/css_website_layout.asp/)
  - [JavaScript](https://www.javascript.com/)
  - [Bootstrap 5](https://getbootstrap.com/)
- Backend:
  - [Java 17](http://www.oracle.com/java/technologies/javase-downloads.html)
  - [Apache Maven >=3.9.8](https://maven.apache.org/download.cgi/)
- Database:
  - [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- Ferramenta
  - [Visual Studio Code (VSCode)](https://code.visualstudio.com)
  - [Postman](http://www.postman.com/downloads/)
  - [Git](https://git-scm.com/downloads)
  - [Docker](https://docs.docker.com/desktop/install/windows-install/)

---

## Instruções de utilização

Essas instruções irão fazer com que tenha uma copia do projeto em sua máquina, podendo ser no intuito de desenvolvimento ou testes.

Pré-requisitos:
  - Java
  - Maven
  - MySQL
  - Docker (Docker-Compose)

<br>

- Passo 1: Clonar o repositório:
  ```bash
  $ git clone https://github.com/Huandemberg/EBStore.git
  ```

<br>

- Passo 2: Configurar e iniciar a API Spring Boot (backend) 

  - Passo 2.1: Entrar no arquivo application.properties:
  ```bash
  $ vi EBStore\src\main\resources\application-dev.properties
  ```

  - Passo 2.2: Configurar as credenciais de banco de dados de acordo com sua instalação do MySQL Server:
  ```proprieties
  # Database config
  spring.datasource.url=jdbc:mysql://localhost:3306/EBStore?useSSL=false&createDatabaseIfNotExist=true
  spring.datasource.username=root
  spring.datasource.password=root
  ```

  - Passo 2.3: Voltar para a pasta raíz do projeto:
  ```bash
  $ cd EBStore\
  ```

  - Passo 2.4: Iniciar a aplicação Spring Boot:
  ```bash
  $ mvn clean install
  ```
  
    - Passo 2.4.1: Iniciar a aplicação Spring Boot utilizando o Maven:
  ```bash
  $ mvn spring-boot:run
  ```

    ou

    - Passo 2.4.1: Iniciar a aplicação utilizando Docker-Compose:
  ```bash
  $ docker-compose up
  ```

  - API estará rodando em http://localhost:8080/

<br>

- Passo 3: Entrar na aplicação frontend após subir a API

  - Passo 3.1: Entrar na pasta raíz do projeto:
  ```bash
  $ cd EBStore\
  ```

  - Passo 3.2: Abrir o arquivo index.html diretamente ou pela extensão Live Server do VsCode:
  ```bash
  $ cd EBStore\view\index.html
  ```

  - Frontend estará rodando em http://127.0.0.1:5500/view/index.html caso iniciado com Live Server.

---
