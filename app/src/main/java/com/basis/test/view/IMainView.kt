package com.basis.test.view

import com.basis.test.model.Pessoa


interface IMainView {
    fun showPessoas(pessoas: List<Pessoa>)
    fun showMensagem(mensagem: String?)
    fun showError(erro: String?)
}