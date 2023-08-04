package com.basis.test.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.caelum.stella.validation.CNPJValidator
import br.com.caelum.stella.validation.CPFValidator
import com.basis.test.R
import com.basis.test.adapter.EnderecoAdapter
import com.basis.test.adapter.PessoaAdapter
import com.basis.test.databinding.ActivityFormBinding
import com.basis.test.model.Endereco
import com.basis.test.model.Pessoa
import com.basis.test.presenter.FormPresenterImpl
import com.basis.test.presenter.MainPresenterImpl
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.where

class FormActivity : AppCompatActivity(), IFormView {

    private lateinit var binding: ActivityFormBinding
    private var pessoa: Pessoa?=null
    private lateinit var presenterImpl: FormPresenterImpl
    private var enderecoAdapter:EnderecoAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenterImpl = FormPresenterImpl()
        presenterImpl.attachView(this)
        setupView()

        val pessoaId = intent.getStringExtra("pessoa_id")
        if (!pessoaId.isNullOrEmpty()) {
            presenterImpl.carregarPessoa(pessoaId)
        }
    }
    private fun preencherCamposComDados(pessoa: Pessoa) {
        this.pessoa = Pessoa()
        this?.pessoa?.id = pessoa.id
        this?.pessoa?.razaoSocial = pessoa.razaoSocial
        this?.pessoa?.cnpj = pessoa.cnpj
        this?.pessoa?.nome = pessoa.nome
        this?.pessoa?.cpf = pessoa.cpf
        this?.pessoa?.dddTelefone = pessoa.dddTelefone
        this?.pessoa?.email = pessoa.email
        this?.pessoa?.enderecos = RealmList()
        pessoa.enderecos?.forEach { endereco -> this.pessoa?.enderecos?.add(endereco) }

        binding.apply {
            if (pessoa.tipoPessoa == "fisica") {
                radioPessoaFisica.isChecked = true
                editTextNome.setText(pessoa.nome)
                editTextCpf.setText(pessoa.cpf)
            } else {
                radioPessoaJuridica.isChecked = true
                editTextRazaoSocial.setText(pessoa.razaoSocial)
                editTextCnpj.setText(pessoa.cnpj)
            }
            editTextDddTelefone.setText(pessoa.dddTelefone)
            editTextEmail.setText(pessoa.email)

            // Preencher os endereços se houver
            pessoa.enderecos?.let {
                listarEndereco()
            }
        }
    }

    private fun setupView() {
        binding.btnAdicionarEndereco.setOnClickListener {
            binding.linearLayoutFormEndereco.visibility = View.VISIBLE
        }

        binding.btnSalvarEndereco.setOnClickListener {
            adicionarEndereco()
            binding.linearLayoutFormEndereco.visibility = View.GONE
            listarEndereco()
        }

        binding.btnSalvar.setOnClickListener {
            if (validarForm()) {
                criarObjetoPessoa()
                pessoa?.let { salvarDadosNoRealm(it) }
                val intent = Intent(this@FormActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.radioGroupTipoPessoa.setOnCheckedChangeListener { group, checkedId ->
            if(binding.radioPessoaFisica.id == checkedId){
                binding.editTextCpf.visibility = View.VISIBLE
                binding.editTextNome.visibility = View.VISIBLE
                binding.editTextCnpj.visibility = View.GONE
                binding.editTextRazaoSocial.visibility = View.GONE
                binding.editTextCpf.text.clear()
                binding.editTextNome.text.clear()
            }else{
                binding.editTextCpf.visibility = View.GONE
                binding.editTextNome.visibility = View.GONE
                binding.editTextCnpj.visibility = View.VISIBLE
                binding.editTextRazaoSocial.visibility = View.VISIBLE
                binding.editTextCnpj.text.clear()
                binding.editTextRazaoSocial.text.clear()
            }
        }
    }

    private fun validarForm(): Boolean {
        val tipoPessoaFisica = binding.radioPessoaFisica.isChecked

        val nome = binding.editTextNome.text.toString().trim()
        val cpf = binding.editTextCpf.text.toString().trim()
        val telefone = binding.editTextDddTelefone.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()

        if (tipoPessoaFisica) {
            if (nome.isEmpty()) {
                exibirMensagemCampoObrigatorio("Nome")
                return false
            }

            if (cpf.isEmpty()) {
                exibirMensagemCampoObrigatorio("CPF")
                return false
            }

            if (telefone.isEmpty()) {
                exibirMensagemCampoObrigatorio("Telefone")
                return false
            }
        } else {
            val razaoSocial = binding.editTextRazaoSocial.text.toString().trim()
            if (razaoSocial.isEmpty()) {
                exibirMensagemCampoObrigatorio("Razão Social")
                return false
            }

            val cnpj = binding.editTextCnpj.text.toString().trim()
            if (cnpj.isEmpty()) {
                exibirMensagemCampoObrigatorio("CNPJ")
                return false
            }

            if (telefone.isEmpty()) {
                exibirMensagemCampoObrigatorio("Telefone")
                return false
            }
        }

        if (email.isEmpty()) {
            exibirMensagemCampoObrigatorio("E-mail")
            return false
        }

        return true
    }

    private fun criarObjetoPessoa(){
        if(pessoa==null)
            pessoa = Pessoa()

        if (binding.radioPessoaFisica.isChecked) {
            pessoa?.tipoPessoa = "fisica"
            pessoa?.nome = binding.editTextNome.text.toString().trim()
            pessoa?.cpf = binding.editTextCpf.text.toString().trim()
            pessoa?.razaoSocial=null
            pessoa?.cnpj=null
        } else {
            pessoa?.tipoPessoa = "juridica"
            pessoa?.razaoSocial = binding.editTextRazaoSocial.text.toString().trim()
            pessoa?.cnpj = binding.editTextCnpj.text.toString().trim()
            pessoa?.nome=null
            pessoa?.cpf=null
        }
        pessoa?.dddTelefone = binding.editTextDddTelefone.text.toString().trim()
        pessoa?.email = binding.editTextEmail.text.toString().trim()
    }

    private fun salvarDadosNoRealm(pessoa: Pessoa) {
        presenterImpl.salvarPessoa(pessoa)
    }

    private fun isValidCPF(cpf: String): Boolean {
        return try {
            val cpfValidator = CPFValidator()
            cpfValidator.assertValid(cpf)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidCNPJ(cnpj: String): Boolean {
        return try {
            val cnpjValidator = CNPJValidator()
            cnpjValidator.assertValid(cnpj)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun adicionarEndereco(){
        val endereco = Endereco()
        if(binding.radioEnderecoComercial.isChecked){
            endereco.tipoEndereco = "comercial"
        }else{
            endereco.tipoEndereco = "residencial"
        }
        endereco.endereco = binding.editTextEndereco.text.toString().trim()
        endereco.numero = binding.editTextNumero.text.toString().trim()
        endereco.complemento = binding.editTextComplemento.text.toString().trim()
        endereco.bairro = binding.editTextBairro.text.toString().trim()
        endereco.cep = binding.editTextCEP.text.toString().trim()
        endereco.cidade = binding.editTextCidade.text.toString().trim()
        endereco.uf = binding.editTextUF.text.toString().trim()
        pessoa?.enderecos?.add(endereco)
    }

    private  fun listarEndereco() {
        pessoa?.enderecos?.let {
            if(enderecoAdapter==null){
                // Crie o adaptador de endereços
                enderecoAdapter = EnderecoAdapter(it)
                // Defina o layout manager e os adaptadores para o RecyclerViews
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.adapter = enderecoAdapter
            }else{
                enderecoAdapter?.updateData(it)
            }
        }
    }

    private fun exibirMensagemCampoObrigatorio(campo: String) {
        exibirMensagem("Campo Obrigatório", "O campo $campo é obrigatório.")
    }

    private fun exibirMensagem(titulo: String, mensagem: String) {
        val alert = AlertDialog.Builder(this@FormActivity)
        alert.setTitle(titulo)
        alert.setMessage(mensagem)
        alert.setPositiveButton("OK") { dialog, id -> finish()  }
        alert.show()
    }

    override fun showMensagem(mensagem: String?) {
        exibirMensagem("Sucesso", mensagem ?: "")
    }

    override fun showError(erro: String?) {
        exibirMensagem("Ocorreu um erro", erro ?: "")
    }

    override fun carregarPessoa(pessoa: Pessoa?) {
        if(pessoa!=null)
            preencherCamposComDados(pessoa)
        else
            exibirMensagem("Erro", "Pessoa não encontrada.")
    }
}
