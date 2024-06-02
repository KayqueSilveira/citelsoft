# Sistema de Processamento de Dados de Candidatos a Doadores de Sangue

## Descrição do Projeto

Este projeto tem como objetivo desenvolver um sistema web para processar e analisar dados de candidatos a doadores de sangue fornecidos por uma agência de banco de sangue. O sistema receberá um JSON com os dados dos candidatos e apresentará os seguintes resultados:

- Todos candidatos
- Quantidade de candidatos por estado no Brasil.
- IMC (Índice de Massa Corporal) médio em cada faixa etária de dez em dez anos: 0 a 10; 11 a 20; 21 a 30, etc.
- Percentual de obesos entre homens e mulheres (considerando obeso quem tem IMC > 30).
- Média de idade para cada tipo sanguíneo.
- Quantidade de possíveis doadores para cada tipo sanguíneo receptor.

## Funcionalidades GET

1. **/api/candidatos/por-estado:**
   - Exibe a quantidade de candidatos presentes na lista para cada estado do Brasil.

2. **/api/candidatos/por-faixa-etaria**
   - Calcula e exibe o IMC médio para faixas etárias de dez em dez anos (0 a 10, 11 a 20, 21 a 30, etc.).

3. **/api/candidatos/percentual-obeso**
   - Calcula e exibe o percentual de candidatos obesos (IMC > 30) entre homens e mulheres.

4. **/api/candidatos/media-idade-sanguineo**
   - Calcula e exibe a média de idade dos candidatos para cada tipo sanguíneo.

5. **/api/candidatos/doadores-tipo-sanguineo**
   - Exibe a quantidade de possíveis doadores para cada tipo sanguíneo receptor.
6. **/api/candidatos**
    - Exibe todos os candidatos
## Tecnologias Utilizadas

- **Backend:**
  - **Java:** Linguagem de programação principal.
  - **Spring Boot:** Framework para simplificação da configuração e desenvolvimento de aplicações Java.
  - **Spring Security:** Framework para segurança da aplicação.
  - **JPA (Java Persistence API):** Para persistência de dados.
  - **Lombok:** Biblioteca para reduzir o boilerplate de código.
  - **Maven:** Ferramenta de automação de compilação e gerenciamento de dependências.
  - **MySQL:** Banco de dados relacional.

- **Comunicação:**
  - **Webhook:** Executar verificação se no banco de dados alguém atingiu o limite de idade.

### Backend (Java, Spring Boot)

- **Controller:** Controladores para receber requisições HTTP e enviar respostas.
- **Service:** Lógica de negócios e processamento de dados.
- **Repository:** Interface para interação com o banco de dados utilizando JPA.
- **Model:** Classes de entidade representando os dados.
- **Configuration:** Configurações de segurança com Spring Security e outras configurações do Spring Boot.

## Como Executar o Projeto

### Pré-requisitos

- Java 11 ou superior
- Maven
- MySQL

### Backend

1. Clone o repositório.
2. Navegue até o diretório do backend.
3. Configure o banco de dados MySQL no arquivo `application.properties`.
4. Execute o comando `mvn clean install` para compilar o projeto.
5. Execute o comando `mvn spring-boot:run` para iniciar o servidor.
