package com.basis.test.presenter

import com.basis.test.model.CadastroModelImpl
import com.basis.test.model.Endereco
import com.basis.test.model.ICadastroModel
import com.basis.test.model.Pessoa
import com.basis.test.view.IFormView

class FormPresenterImpl:IFormPresenter {
    private var view: IFormView? = null
    private val model: ICadastroModel

    init {
        model = CadastroModelImpl()
    }
    override fun attachView(view: IFormView?) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun salvarPessoa(pessoa: Pessoa) {
        model.addPessoa(pessoa)
        if (view != null) {
            view?.showMensagem("Cadastro salvo com sucesso!")
        }
    }

    override fun carregarPessoa(pessoaId: String) {
        val pessoa = model.getPessoa(pessoaId)
        view?.carregarPessoa(pessoa)
    }

    override fun excluirEndereco(endereco: Endereco) {
        TODO("Not yet implemented")
    }
}