Observção, este é um projeto pessoal.
Desenvolvido por mim a medida em que posso trabalhar nele.
Este sistema será implementado aos poucos no local onde trabalho.
E pode, ou deve, ainda apresentar algumas funcionalidades com erros ou em construção.
É meu primeiro projeto sozinho, usando os conceitos que aprendi durante meu tempo de estudo.
Ficarei imensamente feliz em receber feedbacks, opiniões e conselhos.

Hotel Manager 🏨
📌 Tecnologias: Java 21, Spring Boot 3, H2 Database, JPA, Maven

📌 Sobre o Projeto
O Hotel Manager é um sistema de gerenciamento de hotelaria desenvolvido em Java 21 e Spring Boot 3.
Ele permite a administração de clientes , quartos e no futuro reservas, garantindo um controle eficiente dos hóspedes e acomodações.

🚀 Tecnologias Utilizadas
Java 21 - Linguagem principal
Spring Boot 3 - Framework para aplicações web
Spring Data JPA - Para interação com o banco de dados
H2 Database - Banco de dados em memória para testes locais e inicialmente será o banco de dados utilizado na aplicação
Maven - Gerenciador de dependências

📦 Instalação e Execução
Pré-requisitos
Certifique-se de ter instalado:
✅ Java 21
✅ Instale um editor de código, como o [IntelliJ IDEA]

1️⃣ Clonar o repositório

git clone https://github.com/Dutkercz/HotelManager.git
cd HotelManager

🗄️ Banco de Dados
O projeto utiliza H2 Database em memória, com a pasta "data" criada para armazenar os arquivos temporários.

📌 Configuração no application.properties

# URL para salvar os dados em um arquivo no disco
spring.datasource.url=jdbc:h2:file:./data/testhotel
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
📌 Acessando o Console H2:

Após iniciar a aplicação, acesse:
➡️ http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:hotel_db
Usuário: sa
Senha: (vazia por padrão)

📂 Alimentação Inicial do Banco
Para popular o banco de dados com os apartamentos, execute uma unica vez com a aplicação,
o arquivo seedroom que está na pasta test. fazendo a modificações a seus gosto,
mas senguindo o padrão do arquivo.
;
;

HotelManager/
│── src/
│   ├── main/java/com/hotel/
│   │   ├── controllers/   # Controladores da API
│   │   ├── entities/      # Lógica de negócios
│   │   ├── repositories/   # Acesso ao banco de dados
│   │   ├── services/       # Modelos de dados (JPA)
│   │   ├── ui/            # Menu e sub menus
│   │   ├── config/
│   ├── resources/
│   │   ├── application.properties  # defina como: spring.profiles.active=${APP_PROFILE:test}
│   │   ├── application-test.properties # Configurações para test
│── pom.xml  # Arquivo Maven com dependências
│── README.md  # Documentação do projeto




;
;
; A ser concluido. são 6:10 da manhã de 29/01.




