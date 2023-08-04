package com.basis.test.presenter

import com.basis.test.model.Pessoa
import com.basis.test.view.IMainView

interface IMainPresenter {
    fun attachView(view: IMainView?)
    fun detachView()
    fun carregarPessoas(filtro: String?)
    fun excluirPessoa(pessoa: Pessoa)
}