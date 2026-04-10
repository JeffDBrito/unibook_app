
# 📚 Sistema de Gerenciamento de Biblioteca

## 🧾 Descrição Geral

O sistema consiste em uma aplicação web para gerenciamento de bibliotecas, permitindo o controle de livros, autores, categorias, exemplares físicos, empréstimos e multas.

A aplicação tem como objetivo facilitar o controle do acervo, o registro de empréstimos e devoluções, bem como a gestão de usuários e permissões de acesso ao sistema.

O sistema foi modelado com base em conceitos de separação de responsabilidades, permitindo maior flexibilidade e escalabilidade. Entidades como Pessoa, Usuário e Autor são tratadas de forma independente, possibilitando diferentes papéis dentro do sistema.

## 🎯 Objetivos do Sistema
Controlar o acervo de livros da biblioteca
Gerenciar exemplares físicos de cada livro
Registrar empréstimos e devoluções
Controlar atrasos e multas
Gerenciar usuários e permissões
Organizar livros por categorias e autores
Controlar localização física dos livros

# ⚙️ Requisitos Funcionais

## 👤 Gestão de Pessoas e Usuários
- O sistema deve permitir o cadastro de pessoas com dados como nome e e-mail 
- O sistema deve permitir vincular uma pessoa a um usuário do sistema 
- O sistema deve permitir autenticação de usuários (login e senha) 
- O sistema deve permitir definir permissões e papéis (roles) para usuários 
- O sistema deve permitir diferenciar usuários com privilégios administrativos

## ✍️ Gestão de Autores
- O sistema deve permitir cadastrar autores vinculados a uma pessoa
- O sistema deve permitir armazenar biografia dos autores
- O sistema deve permitir associar autores a um ou mais livros

## 📚 Gestão de Livros
- O sistema deve permitir cadastrar livros com título, descrição e data de publicação
- O sistema deve permitir associar um livro a um autor
- O sistema deve permitir associar um livro a uma editora
- O sistema deve permitir classificar livros em uma ou mais categorias

## 🏷️ Gestão de Categorias
- O sistema deve permitir cadastrar categorias de livros
- O sistema deve permitir associar múltiplas categorias a um livro

## 🏢 Gestão de Editoras
- O sistema deve permitir cadastrar editoras
- O sistema deve permitir associar editoras a livros

## 📦 Gestão de Exemplares (Copy)
- O sistema deve permitir cadastrar múltiplos exemplares de um livro
- O sistema deve permitir controlar o status de cada exemplar:
    - Disponível
    - Emprestado
    - Perdido
    - Danificado
- O sistema deve permitir identificar individualmente cada exemplar

## 📍 Controle de Localização (Inventory)
- O sistema deve permitir definir a localização física dos livros
- O sistema deve permitir organizar exemplares por:
    - Setor
    - Prateleira
    - Fileira

# 🔄 Gestão de Empréstimos (Loan)
- O sistema deve permitir registrar empréstimos de exemplares
- O sistema deve associar cada empréstimo a:
    - Um usuário
    - Um exemplar
- O sistema deve registrar:
    - Data do empréstimo
    - Data prevista de devolução
    - Data de devolução
- O sistema deve controlar o status do empréstimo:
    - Ativo
    - Devolvido
    - Cancelado
    - Perdido
- O sistema não deve permitir empréstimo de exemplares indisponíveis

# ⏰ Controle de Atrasos
- O sistema deve identificar automaticamente empréstimos em atraso
- O sistema deve considerar um empréstimo atrasado quando a data atual ultrapassar a data prevista de devolução

# 💸 Gestão de Multas (Fine)
- O sistema deve gerar multas para empréstimos em atraso
- O sistema deve associar a multa a um empréstimo
- O sistema deve permitir registrar o valor da multa
- O sistema deve permitir marcar multas como pagas ou não pagas

# 🔐 Controle de Permissões
- O sistema deve permitir criar permissões
- O sistema deve permitir agrupar permissões em papéis (roles)
- O sistema deve permitir associar papéis a usuários
- O sistema deve controlar o acesso às funcionalidades com base nas permissões

# 🧠 Regras de Negócio
- Um exemplar não pode ser emprestado se estiver indisponível
- Um exemplar não pode possuir mais de um empréstimo ativo simultaneamente
- Um empréstimo só pode ser finalizado com a devolução do exemplar
- Um empréstimo em atraso pode gerar multa automaticamente
- Um usuário pode ser impedido de realizar novos empréstimos caso possua multas pendentes

<br>

# 🚀 Considerações Finais

O sistema foi projetado com foco em boas práticas de modelagem e separação de responsabilidades, permitindo fácil manutenção e evolução. A utilização de entidades como Copy para controle individual de exemplares e Loan para histórico de empréstimos garante maior precisão no gerenciamento do acervo.