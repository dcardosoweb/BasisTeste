package com.basis.test.model

interface ICadastroModel {
    fun getPessoas():List<Pessoa>?
    fun addPessoa(pessoa: Pessoa?)
    fun updatePessoa(pessoa: Pessoa?)
    fun deletePessoa(pessoa: Pessoa?)
}