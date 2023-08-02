package com.basis.test.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

open class Pessoa: RealmObject() {
    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var nome: String? = null
    var cpf: String? = null
    var razaoSocial: String? = null
    var cnpj: String? = null
    var tipoPessoa: String? = null
    var dddTelefone: String? = null
    var telefone: String? = null
    var email: String? = null
    var enderecos: RealmList<Endereco>? = null
}