# HotelManager 🏨

**Observação:** Este é um projeto pessoal.  
É meu primeiro projeto, e meu desejo é que ele se torne útil no meu dia a dia, facilitando o acesso ao histórico de hospedagens e armazenamento de dados.  
Utilizei alguns dos conceitos que aprendi durante meu tempo de aprendizado. Ficarei imensamente feliz em receber feedbacks, opiniões e conselhos.

---

## Desenvolvedor

Este projeto foi desenvolvido por [Cristian Dutkercz](https://github.com/Dutkercz)

## Contato
- **GitHub**: [Dutkercz](https://github.com/Dutkercz)
- **LinkedIn**: [Cristian T. Dutkercz Rosa](https://linkedin.com/in/cristiandutkercz)
- **E-mail**: dutkercz@gmail.com  
- **Instagram**: [Cristian Dutkercz](https://www.instagram.com/cristiandutkercz)  

---

## Sobre o Projeto

HotelManager é um sistema de gerenciamento de hotel que combina um **frontend em Java Swing** com um **backend em Spring Boot**.  
Este projeto permite o gerenciamento de apartamentos, clientes e hospedagens, incluindo funcionalidades como check-in, check-out e visualização do status dos apartamentos.

---

## Funcionalidades 🚀
- **Gerenciamento de apartamentos**: Visualização de apartamentos livres e ocupados, com o nome do hóspede nos ocupados.
- **Gerenciamento de clientes**: Cadastro, edição e visualização de informações dos clientes.
- **Gerenciamento de hospedagens**:
  - Check-in: Seleção de apartamento e registro da hospedagem.
  - Check-out: Resumo de cobrança, liberação do apartamento e finalização da hospedagem.

---

## Tecnologias Utilizadas 🚀
- **Java Swing**: Interface gráfica.
- **Spring Boot**: Backend para gerenciamento de dados e lógica de negócio.
- **Spring Data JPA**: Para interação com o banco de dados.
- **Banco de Dados H2**: Persistência em modo de arquivo, com criação automática da pasta `data` para armazenamento dos dados.

---

## Requisitos 🚀
Certifique-se de ter as seguintes dependências instaladas:
- Java 21
- Maven
- IDE (caso queira executar diretamente do editor de código)

---

## Configuração do Banco de Dados 🚀
O sistema utiliza o banco de dados **H2** no modo de arquivo.  
A primeira execução do projeto cria automaticamente a pasta `data` para armazenar os dados persistentes.

---

## Como Executar 🚀

### Opção 1: Através da IDE
1. Abra o projeto em sua IDE preferida (por exemplo, IntelliJ ou Eclipse).
2. Localize a classe `Application` no pacote principal do projeto.
3. Execute a classe `Application` para iniciar o sistema.

### Opção 2: Via Terminal
1. Clone o repositório:
   ```bash
   git clone https://github.com/Dutkercz/HotelManager.git

   cd HotelManager

   mvn clean package

   java -jar target/HotelManager.jar

## Estrutura do Projeto 🚀

Frontend (Swing): Gerencia a interface com o usuário para interação com os apartamentos, clientes e hospedagens.
Backend (Spring Boot): Fornece serviços e persistência de dados, conectando-se ao banco de dados H2.
Banco de Dados H2: Configurado para persistir os dados no diretório data.

## Instruções de Uso 🚀

Tela inicial:  
  - Navegue pelas opções de clientes, apartamentos ou hospedagens.

Gerenciamento de apartamentos:  
  - Visualize apartamentos livres ou ocupados.
  - Veja o nome do hóspede nos apartamentos ocupados.

Gerenciamento de hospedagens:  
  - Realize check-in escolhendo o apartamento e registrando a hospedagem.
  - Realize check-out escolhendo o apartamento ocupado, visualize o resumo de cobrança e finalize a hospedagem.

Gerenciamento de clientes:  
  - Cadastre novos clientes, edite informações ou consulte dados existentes.

## Planejamento Futuro 🚀
  - Adicionar cobranças extras ao check-out, como consumo do bar.
  - Melhorar o design da interface utilizando ferramentas mais modernas.
  - Implementar relatórios gerenciais.

## ============================================================
