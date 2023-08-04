package com.basis.test.view

import com.basis.test.model.Pessoa

interface IFormView {
    fun showMensagem(mensagem: String?)
    fun showError(erro: String?)
    fun carregarPessoa(pessoa: Pessoa?)
}