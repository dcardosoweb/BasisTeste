package com.basis.test.model

interface ICadastroModel {
    fun getPessoas(filtro: String?): List<Pessoa>?
    fun getPessoa(pessoaIs: String): Pessoa?
    fun addPessoa(pessoa: Pessoa)
    fun updatePessoa(pessoa: Pessoa)
    fun deletePessoa(pessoa: Pessoa)
}