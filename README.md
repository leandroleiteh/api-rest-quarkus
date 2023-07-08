# Quarkus Task Manager

O Quarkus Task Manager é um projeto de exemplo que demonstra a criação de uma API RESTful de gerenciamento de clientes utilizando o framework Quarkus.


## Instalação

1. Clone o repositório para o seu ambiente local:

https://github.com/leandroleiteh/APIREST-quarkus.git


2. Acesse o diretório do projeto:

cd quarkus-task-manager


3. Execute o aplicativo em modo de desenvolvimento:

mvn quarkus:dev



## Uso

O Quarkus Task Manager fornece endpoints REST para criar, atualizar, listar e excluir clientes. A API possui os seguintes endpoints:

- `GET /customers`: Retorna todos os clientes cadastrados.
- `POST /customers`: Cria um novo cliente.
- `PUT /customers/{id}`: Atualiza um cliente existente pelo ID.
- `DELETE /customers/{id}`: Exclui um cliente pelo ID.

## Tecnologias utilizadas

- Quarkus - framework Java nativo para a nuvem.
- RESTEasy - framework para criação de APIs RESTful.
- Hibernate ORM - mapeamento objeto-relacional para persistência de dados.
- H2 Database - banco de dados em memória para desenvolvimento e teste.
- Maven - gerenciamento de dependências e build do projeto.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir um pull request com melhorias, correções de bugs ou novas funcionalidades.

## Autores

- [Leandro Leite](https://github.com/leandroleiteh)

## Licença

Este projeto é licenciado sob a [...](LICENSE).


