# Projeto de Gerenciamento de Pacientes e Consultas

Este é um projeto Java desenvolvido para gerenciar informações de pacientes e consultas médicas. Ele fornece uma interface gráfica simples para cadastrar pacientes, agendar consultas e gerar relatórios.

## Funcionalidades

* **Cadastro de Pacientes:** Permite cadastrar informações detalhadas sobre pacientes, como nome, CPF, data de nascimento, telefone, endereço e email.
* **Agendamento de Consultas:** Permite agendar consultas médicas, associando-as a um paciente e especificando data, hora e médico.
* **Relatórios:** Gera relatórios com informações sobre pacientes e consultas agendadas.
* **Interface Gráfica:** Possui uma interface gráfica intuitiva para facilitar o uso do sistema.

## Como Executar o Projeto

1.  **Pré-requisitos:**

    * Java Development Kit (JDK) 8 ou superior instalado.
    * Ambiente de Desenvolvimento Integrado (IDE) como Eclipse, IntelliJ ou NetBeans (opcional, mas recomendado).
2.  **Clonar o Repositório:**

    \`\`\`bash
    git clone https://github.com/Alexandrepujhas/SistemaUPA.git
    cd SistemaUPA
    \`\`\`
3.  **Compilar o Projeto:**

    * **Usando o IDE:** Importe o projeto no seu IDE e compile-o.
    * **Usando o Terminal:** Se estiver usando o terminal, navegue até o diretório do projeto e execute o seguinte comando:

        \`\`\`bash
        javac view/\*.java controller/\*.java model/\*.java
        \`\`\`
4.  **Executar o Projeto:**

    * **Usando o IDE:** Execute a classe `TelaPrincipal` no seu IDE.
    * **Usando o Terminal:** Execute o seguinte comando:

        \`\`\`bash
        java view.TelaPrincipal
        \`\`\`

## Estrutura do Projeto

O projeto está organizado nas seguintes pastas:

* `controller`: Contém as classes controladoras, que lidam com a lógica da aplicação e a interação entre a view e o model.
* `model`: Contém as classes que representam as entidades do sistema, como `Paciente` e `Consulta`.
* `view`: Contém as classes que definem a interface gráfica do sistema, como `PacienteCard`, `ConsultaCard` e `TelaPrincipal`.

## Como Contribuir

1.  **Fork o Repositório:** Faça um fork do repositório para a sua conta do GitHub.
2.  **Crie uma Branch:** Crie uma branch para a sua feature ou correção de bug:

    \`\`\`bash
    git checkout -b minha-feature
    \`\`\`
3.  **Faça as Alterações:** Faça as alterações no código.
4.  **Commit as Alterações:** Commit as alterações com uma mensagem descritiva:

    \`\`\`bash
    git commit -m "Adiciona nova funcionalidade X"
    \`\`\`
5.  **Envie as Alterações:** Envie as alterações para o seu repositório no GitHub:

    \`\`\`bash
    git push origin minha-feature
    \`\`\`
6.  **Crie um Pull Request:** Crie um pull request para o repositório original.

## Próximos Passos

* Implementar a funcionalidade de edição de pacientes e consultas.
* Adicionar persistência dos dados em banco de dados.
* Melhorar a interface gráfica.
* Implementar mais relatórios.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
