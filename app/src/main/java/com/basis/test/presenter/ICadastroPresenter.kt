package com.basis.test.presenter

import com.basis.test.model.Pessoa
import com.basis.test.view.ICadastroPessoaView

interface ICadastroPresenter {
    fun attachView(view: ICadastroPessoaView?)
    fun detachView()
    fun carregarPessoas()
    fun adicionarPessoa(pessoa: Pessoa?)
    fun editarPessoa(pessoa: Pessoa?)
    fun excluirPessoa(pessoa: Pessoa?)
}