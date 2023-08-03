package com.basis.test.presenter

import com.basis.test.model.CadastroModelImpl
import com.basis.test.model.ICadastroModel
import com.basis.test.model.Pessoa
import com.basis.test.view.ICadastroPessoaView

class CadastroPresenterImpl : ICadastroPresenter {
    private var view: ICadastroPessoaView? = null
    private val model: ICadastroModel

    init {
        model = CadastroModelImpl()     }

    override fun attachView(view: ICadastroPessoaView?) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun carregarPessoas(filtro: String?) {
        val pessoas: List<Pessoa>? = model.getPessoas(filtro)
        view?.showPessoas(pessoas)
    }

    override fun adicionarPessoa(pessoa: Pessoa) {
        model.addPessoa(pessoa)
        if (view != null) {
            view?.showMensagem("Pessoa adicionada com sucesso!")
        }
    }

    override fun editarPessoa(pessoa: Pessoa) {
        model.updatePessoa(pessoa)
        if (view != null) {
            view?.showMensagem("Pessoa atualizada com sucesso!")
        }
    }

    override fun excluirPessoa(pessoa: Pessoa) {
        model.deletePessoa(pessoa)
        if (view != null) {
            view?.showMensagem("Pessoa excluída com sucesso!")
        }
    }
}
