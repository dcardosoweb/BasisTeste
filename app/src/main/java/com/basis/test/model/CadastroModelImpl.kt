package com.basis.test.model

import com.basis.test.realmmananger.RealmManager

class CadastroModelImpl : ICadastroModel {
    private val realm = RealmManager()
    // Retorne a lista de pessoas do Realm

    override fun getPessoas(filtro: String?): List<Pessoa>? {
        return realm.getAllPessoasByFiltro(filtro)
    }

    override fun getPessoa(pessoaId: String): Pessoa? {
        return realm.getPessoa(pessoaId)
    }

    override fun addPessoa(pessoa: Pessoa) {
       realm.addOrUpdate(pessoa)
    }

    override fun updatePessoa(pessoa: Pessoa) {
        realm.addOrUpdate(pessoa)
    }

    override fun deletePessoa(pessoa: Pessoa) {
        realm.deleteById(pessoa.id)
    }
}
