package ui.screen;

/**
 * Define os identificadores únicos para todas as telas disponíveis no sistema.
 * É utilizada pelo {@link TelaManager} para orquestrar a navegação entre as diferentes
 * interfaces de interação com o usuário.
 */
public enum TelaEnum {
    /** Menu principal de acesso aos módulos do sistema. */
    MENU_PRINCIPAL,
    /** Tela para registro de novos empréstimos. */
    REGISTRO_DE_EMPRESTIMO,
    /** Tela para registro de devoluções de livros. */
    REGISTRO_DE_DEVOLUCAO,
    /** Tela para cadastro de novos exemplares no acervo. */
    CADASTRAR_LIVRO,
    /** Tela para busca e edição de livros existentes. */
    PESQUISAR_LIVRO,
    /** Tela para inclusão de novos usuários (alunos ou docentes). */
    CADASTRAR_USUARIO,
    /** Tela para busca e edição de dados de usuários. */
    PESQUISAR_USUARIO,
    /** Tela para consulta de livros atualmente emprestados. */
    CONSULTA_EMPRESTADOS,
    /** Tela para consulta de histórico de livros já devolvidos. */
    CONSULTA_DEVOLVIDOS,
    /** Tela para consulta de empréstimos filtrados por um livro específico. */
    CONSULTA_POR_LIVRO,
    /** Tela para consulta de histórico de empréstimos por usuário. */
    CONSULTA_HISTORICO_USUARIO
}