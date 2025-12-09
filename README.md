# ChatKumi-BackEnd

Um backend de chatbot para academia Cross Kumite de artes marciais e ginástica, desenvolvido em **Java com Spring Boot**. O usuário pode encontrar nesse sistema informações sobre aulas, agendamento de aulas experimentais, dúvidas frequentes e modalidades.

# Front-End

Para testar o chatbot é necessário o Front-end, que pode ser utilizado das seguintes formas:

- [ACESSO AO WEBSITE](https://chat-kumi-front-end.vercel.app) (Não é preciso instalação do front ou back-end)
- [CLONAGEM DO REPOSITÓRIO](https://github.com/ChatBot-Kumi/ChatKumi-FrontEnd) (Pode ser usado com back-end local, mas não é necessário)

## Funcionalidades

### 1. **Menu Principal**
- Consulta sobre aulas para crianças
- Consulta sobre aulas para adultos
- Visualização de dúvidas frequentes
- Agendamento de aula experimental

### 2. **Modalidades**
Existem duas categorias de modalidades:
- Modalidades infantis e para adultos
Ao escolher uma modalidade de uma das listas, encontra-se as seguintes informações:
- Faixas etárias permitidas
- Turmas e horários

### 3. **Dúvidas Frequentes**
- Informações sobre Gym Pass
- Horários de funcionamento
- Formas de pagamento
- Processo de matrícula e aulas experimentais

### 4. **Sistema de Agendamento**
O usuário passa pelo seguinte fluxo: 
1. **Coleta de Nome** - Validação de entrada
2. **Coleta de Idade** - COm validação de faixa etária e entrada
3. **Seleção de Modalidade** - Com validação de compatbilidade com idade
4. **Seleção de Turma** - Escolha por uma lista baseada na modalidade e idade
5. **Seleção de Data** - Próximas 4 datas (1 mês) disponíveis para o dia da semana da turma
6. **Confirmação** - Informações completas sobre o agendamento



## 🛠️ Tecnologias

- **Java 11+**
- **Spring Boot** - Framework web
- **Spring Stereotype Service** - Injeção de dependência
- **Maven** - Gerenciador de dependências
- **Collections Framework** - HashMap, ArrayList, List, Map

## 🚀 Como Executar

### Pré-requisitos
- JDK 11 ou superior
- Maven 3.6+
- Git

### Passos

1. **Clone o repositório**
   ```bash
   git clone https://github.com/ChatBot-Kumi/ChatKumi-BackEnd.git
   cd ChatKumi-BackEnd
   ```

2. **Compile o projeto**
   ```bash
   .\mvnw.cmd compile
   ```
   (Linux/Mac: `./mvnw compile`)

3. **Execute a aplicação**
   ```bash
   .\mvnw.cmd spring-boot:run
   ```
   (Linux/Mac: `./mvnw spring-boot:run`)

4. **Acesse o serviço**
   - O serviço estará disponível em: `http://localhost:8080`

## 📡 API REST

### Endpoint Principal

**POST** `/api/chatbot/mensagem`

Envia uma mensagem ao chatbot e recebe a resposta.

**Request:**
```json
{
  "mensagem": "Olá"
}
```

**Response:**
```json
{
  "resposta": "Como posso te ajudar hoje?\nEscolha uma opção:\n1. Aulas para Crianças (Kids)\n2. Aulas para Adultos (Adulto)\n3. Dúvidas Frequentes (Duvidas)\n4. Agendar Aula Experimental (Agendar)\nDigite 'Sair' a qualquer momento."
}
```

## 💬 Exemplos de Interação

### Fluxo de Agendamento
```
Usuário: Olá
Bot: Como posso te ajudar hoje?
      1. Aulas para Crianças (Kids)
      2. Aulas para Adultos (Adulto)
      3. Dúvidas Frequentes (Duvidas)
      4. Agendar Aula Experimental (Agendar)

Usuário: Agendar
Bot: Certo! Vamos agendar sua aula experimental.
     1 - Qual o nome do aluno que fará a aula?

Usuário: João Silva
Bot: 1. Nome validado com sucesso.
     2 - Qual a idade de João Silva?

Usuário: 15
Bot: 2. Idade validada (15 anos).
     3 - Para qual aula João Silva gostaria de agendar a experimental?
     Modalidades disponíveis: Karatê, Judo, Taekwondo.

Usuário: Karatê
Bot: 3. Modalidade **KARATÊ** selecionada.
     4 - Para qual turma? (Compatível com 15 anos)
     1. Segunda e Quarta às 15:00
     2. Terça e Quinta às 17:00
     ...
```

## 🔄 Fluxo de Estados

O chatbot utiliza uma máquina de estados para gerenciar a conversa:

```
MENU_PRINCIPAL
    ↓
[Kids] → AULAS_INFANTIL → consulta modalidades
[Adulto] → AULAS_ADULTO → consulta modalidades
[Dúvidas] → DUVIDAS → consulta FAQ
[Agendar] → AGENDAMENTO_COLETANDO_NOME
    ↓
AGENDAMENTO_COLETANDO_IDADE
    ↓
AGENDAMENTO_SELECIONANDO_MODALIDADE
    ↓
AGENDAMENTO_SELECIONANDO_TURMA (ou direto para DATA se única turma)
    ↓
AGENDAMENTO_SELECIONANDO_DATA
    ↓
AGENDAMENTO_FINALIZADO → MENU_PRINCIPAL
```

## 👥 Contribuidores

- [ChatBot-Kumi](https://github.com/ChatBot-Kumi)
