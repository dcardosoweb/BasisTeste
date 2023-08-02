package com.basis.test.model

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class Endereco (
   var tipoEndereco: String? = null,
   var endereco: String? = null,
   var numero: String? = null,
   var complemento: String? = null,
   var bairro: String? = null,
   var cep: String? = null,
   var cidade: String? = null,
   var uf: String? = null
): RealmObject() {}