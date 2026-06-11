package ui.screen;

/**
 * Lista com todas as telas que existem no nosso sistema.
 * Usamos isso para trocar de tela com segurança, sem correr o risco de errar 
 * o nome digitando uma String de texto solta.
 */
public enum TelaEnum {
    MENU_PRINCIPAL,
    REGISTRO_DE_EMPRESTIMO,
    REGISTRO_DE_DEVOLUCAO,
    CADASTRAR_LIVRO,
    PESQUISAR_LIVRO,
    CADASTRAR_USUARIO,
    PESQUISAR_USUARIO,
    HISTORICO
}
