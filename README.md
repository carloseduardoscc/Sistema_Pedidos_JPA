# 📚 Library API - Projeto de Estudo com JPA

## 🧠 Sobre o Projeto

Este projeto foi desenvolvido com o objetivo de consolidar conhecimentos em **JPA (Java Persistence API)**, aplicando na prática conceitos fundamentais de persistência de dados em aplicações Java.

A proposta não é ser uma aplicação completa ou pronta para produção, mas sim um ambiente controlado para experimentação e aprendizado de conceitos importantes, como mapeamento de entidades, relacionamento entre tabelas e configuração de infraestrutura de banco de dados.

---

## 🚀 Tecnologias Utilizadas

- Java
- JPA (Hibernate como implementação)
- PostgreSQL
- HikariCP
- Docker
- PgAdmin

---

## 🏗️ Estrutura do Projeto

O projeto segue uma separação em camadas, visando organização e clareza:

### 📦 `model`
Responsável pelas entidades da aplicação.

- Mapeamento com `@Entity`
- Definição de chaves primárias (`@Id`)
- Relacionamentos (`@OneToMany`, `@ManyToOne`, etc.)
- Constraints e validações básicas

---

### 📦 `repository`
Camada de acesso a dados.

- Interfaces responsáveis por operações de persistência
- Uso de abstrações do JPA para consultas e manipulação de entidades

---

### 📦 `service`
Camada de regra de negócio.

- Orquestra chamadas entre repositórios
- Contém lógica da aplicação (mesmo que simples, já começa a separar responsabilidades)

---

### 📦 `conf`
Configuração manual de infraestrutura.

- Configuração do **HikariCP** como pool de conexões
- Definição de parâmetros de conexão com banco
- Setup desacoplado da configuração padrão automática

---

## 🧪 Testes

Os testes realizados são **manuais**, com foco em validar:

- Persistência de entidades
- Relacionamentos entre tabelas
- Comportamento de consultas

Esse formato foi adotado para facilitar a observação direta do comportamento do JPA durante o aprendizado.

---

## 🐳 Banco de Dados

- PostgreSQL rodando via **Docker**
- Gerenciamento e visualização utilizando **PgAdmin**

Essa abordagem permite simular um ambiente mais próximo do real, além de facilitar a configuração e isolamento do banco.

---

## 🎯 Objetivos de Aprendizado

- Entender o funcionamento do JPA na prática
- Trabalhar com mapeamento objeto-relacional (ORM)
- Explorar relacionamentos entre entidades
- Configurar manualmente um pool de conexões (HikariCP)
- Integrar aplicação Java com banco PostgreSQL via container

---

## ⚠️ Observações

Este projeto tem caráter **educacional** e foi desenvolvido como parte da prática de um módulo já concluído de estudos.

Alguns pontos como tratamento de exceções, validações mais robustas e testes automatizados podem não estar completamente implementados, pois o foco principal está no aprendizado dos conceitos de persistência.

---

## 🤝 Considerações Finais

Esse projeto representa um passo importante na construção de base sólida em persistência de dados com Java. Mais do que um sistema completo, ele é um laboratório de aprendizado — e isso tem muito valor no processo de evolução como desenvolvedor.
