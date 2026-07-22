# Sistema de Gestão de Posto de Combustíveis

API REST desenvolvida com **Java 21** e **Spring Boot** para gerenciamento de tipos de combustível, bombas e abastecimentos.

O projeto foi criado como desafio técnico e posteriormente aprimorado com foco em organização, boas práticas, testes automatizados, versionamento do banco de dados e documentação da API.

## Tecnologias

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Flyway
* JUnit 5
* Mockito
* AssertJ
* Swagger / OpenAPI
* Maven

## Funcionalidades

* Cadastro, consulta, atualização e exclusão de tipos de combustível
* Cadastro e gerenciamento de bombas
* Registro de abastecimentos
* Cálculo automático do valor total do abastecimento
* Validação de regras de negócio
* Tratamento global de exceptions
* Migrations de banco de dados com Flyway
* Testes unitários da camada de serviço
* Documentação interativa com Swagger

## Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/Gustavobrw/Desafio-Posto-de-Combustiveis.git
```

Acesse a pasta do projeto:

```bash
cd Desafio-Posto-de-Combustiveis
```

Configure a conexão com o PostgreSQL no `application.properties` e execute:

```bash
mvn spring-boot:run
```

A documentação da API estará disponível em:

```text
http://localhost:8080/swagger
```

## Executando os testes

```bash
mvn test
```

## Estrutura do projeto

```text
controller
service
repository
entity
dto
mapper
exception
config
```

## Objetivo

Este projeto demonstra conhecimentos em:

* Desenvolvimento de APIs REST
* Arquitetura em camadas
* Persistência de dados com JPA e Hibernate
* Versionamento de banco de dados com Flyway
* Tratamento de exceptions e regras de negócio
* Testes unitários com JUnit, Mockito e AssertJ
* Documentação de APIs com Swagger/OpenAPI
