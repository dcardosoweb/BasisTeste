package com.basis.test.model

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

open class Endereco : RealmObject(),Serializable {
   var tipoEndereco: String? = null // "residencial" ou "comercial"
   var endereco: String? = null
   var numero: String? = null
   var complemento: String? = null
   var bairro: String? = null
   var cep: String? = null
   var cidade: String? = null
   var uf: String? = null

   fun getEnderecoFormatado():String{
      var endereco = StringBuilder()
      endereco.append(this.endereco)
      endereco.append(" Numero: ${numero ?: "-"} ")
      endereco.append(" Complemento: ${complemento ?: "-"} ")
      endereco.append(" Bairro: ${bairro} ")
      endereco.append(" CEP: ${cep} ")
      endereco.append(" Cidade/UF: ${cidade}/${uf}")
      return endereco.toString()
   }
}