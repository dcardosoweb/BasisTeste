package com.basis.test.presenter

import com.basis.test.model.Endereco
import com.basis.test.model.Pessoa
import com.basis.test.view.IFormView

interface IFormPresenter {
    fun attachView(view: IFormView?)
    fun detachView()
    fun salvarPessoa(pessoa: Pessoa)
    fun excluirEndereco(endereco: Endereco)
    fun carregarPessoa(pessoaId: String)
}