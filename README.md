# IACAI - Inteligência Artificial da Acaiacá 🤖🍃

## Descrição do Projeto 🌱

O projeto **IACAI** é uma inteligência artificial desenvolvida para a plataforma **Acaiacá**. O objetivo principal é proporcionar uma experiência inovadora aos usuários, permitindo interações rápidas e inteligentes, com respostas automatizadas para facilitar a comunicação dentro da plataforma.

A **Acaiacá** é uma plataforma digital voltada para conectar compradores e vendedores, com foco na valorização dos pequenos agricultores e na promoção de práticas sustentáveis. Nossa missão é:

- **Fortalecer pequenos agricultores** 🌾
- **Promover a sustentabilidade** 🌍
- **Conectar colheitas a pessoas conscientes** 🍅
- **Valorizar alimentos cultivados** 🥕
- **Construir uma rede justa e humana** 🤝
- **Cultivar o futuro com cuidado e esperança** 🌿

Este é um projeto desenvolvido como parte do programa **PROA**, para o projeto **Demoday** do programa, que visa incentivar a criação de projetos que tragam **valor à comunidade**, sejam **sustentáveis**, **rentáveis** e **transformadores**.

---

## Tecnologias Utilizadas ⚙️

O projeto **IACAI** foi desenvolvido com as seguintes tecnologias:
<div align="center">
  <img src="https://img.shields.io/badge/Java-17+-orange" alt="Java 17+">
  <img src="https://img.shields.io/badge/Vaadin-24+-8E24AA" alt="Vaadin 24+">
  <img src="https://img.shields.io/badge/Spring_Boot-3.1-green" alt="Spring Boot 3.1">
  <img src="https://img.shields.io/badge/CSS3-1572B6?logo=css3" alt="CSS3">
  <img src="https://img.shields.io/badge/OpenAI-GPT-412991" alt="OpenAI GPT">
</div>

- **Java**: Linguagem de programação principal para o backend.
- **Vaadin**: Framework utilizado para construir a interface do usuário.
- **Spring Boot**: Framework para criação da API REST.
- **CSS Personalizado**: Estilo visual personalizado para a interface.
- **Inteligência Artificial**: Algoritmos baseados em aprendizado de máquina para a automação das interações com os usuários.

## 📋 Pré-requisitos para instalação e utilização local:

Antes de começar, você precisará ter instalado:

- ☕ **Java JDK 17 ou superior**
- 📦 **Maven 3.6+**
- 🐙 **Git**
- 🔑 **Conta na OpenAI** (para obter API Key)

## 🚀 Começando

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/IAcai.git
cd IAcai
```
### 2. Configurar API Key
Acesse OpenAI e obtenha sua API Key

Crie um arquivo `.env` na raiz do projeto com:

```properties
OPENAI_API_KEY=sua-chave-aqui
```
### 3. Construir o projeto
```bash
mvn clean install
```
### 4. Executar a aplicação
```bash
mvn spring-boot:run
```
🌐 Acessando a aplicação
Acesse no seu navegador:

`http://localhost:8080`

🛠️ Estrutura do Projeto
```
IAcai/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/acaiaca/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       └── IAcaiApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
├── .gitignore
├── pom.xml
└── README.md
```
🤖 Funcionalidades
💬 Chat inteligente com integração OpenAI

🌿 Respostas contextualizadas sobre agricultura sustentável

⚡ Processamento rápido de mensagens

🔄 Histórico de conversação

🆘 Solução de Problemas
| Problema | Solução |
|---|---|
| Erro Java Version | Verifique se tem JDK 17+ instalado |
| API Key inválida | Confirme se a chave foi colocada corretamente no `.env` |
| Porta 8080 ocupada | Altere `server.port` no `application.properties` |
| API Key inválida | Confirme se há créditos para o consumo de tokens em `billing` na `https://platform.openai.com/` |


📄 Licença
Este projeto está licenciado sob a MIT License.

<div align="center"> Feito com ❤️ pela equipe Acaiacá | 🌍 Por uma agricultura mais sustentável </div>

