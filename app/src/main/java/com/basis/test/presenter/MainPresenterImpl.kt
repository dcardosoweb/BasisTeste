package com.basis.test.presenter

import com.basis.test.model.CadastroModelImpl
import com.basis.test.model.ICadastroModel
import com.basis.test.model.Pessoa
import com.basis.test.view.IMainView

class MainPresenterImpl : IMainPresenter {
    private var view: IMainView? = null
    private val model: ICadastroModel

    init {
        model = CadastroModelImpl()
    }

    override fun attachView(view: IMainView?) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun carregarPessoas(filtro: String?) {
        val pessoas: List<Pessoa>? = model.getPessoas(filtro)
        view?.showPessoas(pessoas ?: emptyList() )
    }

    override fun excluirPessoa(pessoa: Pessoa) {
        model.deletePessoa(pessoa)
        if (view != null) {
            view?.showMensagem("Pessoa excluída com sucesso!")
        }
    }
}
