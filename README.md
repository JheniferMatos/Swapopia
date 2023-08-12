# SWAPOPIA, UM MUNDO DE TROCAS
# Projeto RPG em Java com Spring Boot e REST

Este é um projeto de um RPG desenvolvido em Java utilizando o framework Spring Boot para a criação de uma API REST. O projeto permite que os usuários se registrem, autentiquem, comprem caixas, abram caixas, troquem itens entre si e realizem outras ações relacionadas ao mundo do RPG.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- RESTful API
- Banco de Dados Relacional (ex: MySQL, PostgreSQL)
- Postman (para testar as requisições da API)

## Estrutura do Projeto

O projeto está organizado nas seguintes camadas:

- **Model**: Contém as classes de entidade que representam os dados do projeto.
- **Repository**: Contém interfaces que estendem o Spring Data JPA para realizar operações no banco de dados.
- **Controller**: Responsável por receber as requisições HTTP, processar as ações e retornar as respostas.
- **Service**: Lida com a lógica de negócio e atua como intermediário entre o Controller e o Repository.
- **Security**: Configurações de segurança, autenticação e autorização.

## Funcionalidades

O projeto RPG em Java com Spring Boot e REST oferece as seguintes funcionalidades:

### Registro e Autenticação

- Os usuários podem se registrar fornecendo nome, login e senha.
- A autenticação é feita utilizando login e senha.

### Compra de Caixas

- Os usuários podem comprar caixas de diferentes tipos.
- As caixas podem conter itens aleatórios com base nas definições.

### Abertura de Caixas

- Os usuários podem abrir caixas e receber itens aleatórios.
- Itens recebidos são baseados no tipo de caixa e suas definições.

### Troca de Itens

- Os usuários podem propor trocas de itens com outros usuários.
- As propostas de troca podem ser aceitas ou recusadas.
- O status das trocas é salvo no banco de dados.

### Atualização de Perfil

- Os usuários podem atualizar seu nome e foto de perfil.

### Venda de Itens

- Os usuários podem vender itens específicos que possuem em seu inventário.
- O valor de venda é determinado pelo valor do item ao ser aberto.

### Visualização de Inventário

- Os usuários podem visualizar os itens e caixas que possuem em seu inventário.


## Como Executar o Projeto

1. Clone este repositório para sua máquina local.
2. Instale o [MySQL](https://www.mysql.com/) ou outro banco de dados relacional.
3. Abra o projeto em sua IDE favorita
4. Configure as propriedades do banco de dados no arquivo `src/main/resources/application.properties`.
5. Dentro da pasta do projeto, execute o comando `mvn clean install` para instalar as dependências.
6. Execute o comando `mvn spring-boot:run` para iniciar a aplicação.
7. Utilize o Postman ou outro cliente de API para testar as diferentes rotas e funcionalidades.

## Contribuição

Este projeto é de código aberto e as contribuições são bem-vindas! Sinta-se à vontade para fazer um fork, implementar melhorias e enviar um pull request.


