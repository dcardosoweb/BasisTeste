package com.basis.test.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.basis.test.adapter.EnderecoAdapter
import com.basis.test.adapter.PessoaAdapter
import com.basis.test.databinding.ActivityMainBinding
import com.basis.test.model.Pessoa
import com.basis.test.presenter.MainPresenterImpl

class MainActivity() : AppCompatActivity(), IMainView{

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenterImpl: MainPresenterImpl
    private var pessoaAdapter: PessoaAdapter? = null
    private lateinit var enderecoAdapter: EnderecoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenterImpl = MainPresenterImpl()
        presenterImpl.attachView(this)
        presenterImpl.carregarPessoas(null)
        setupView()
    }

    private fun setupView() {
        binding.btnPesquisar.setOnClickListener {
            val nomePesquisado = binding.editTextPesquisar.text.toString().trim()
            filtrarPorNome(nomePesquisado)
        }

        binding.btnAdicionar.setOnClickListener {
            val intent = Intent(this@MainActivity, FormActivity::class.java)
            startActivity(intent)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        // Obtenha todas as pessoas do Realm



    }

    private fun filtrarPorNome(nome: String) {
        presenterImpl.carregarPessoas(nome)
    }

    fun onPessoaLongClick(pessoa: Pessoa) {
        // Lógica para editar os dados da pessoa aqui
        // Por exemplo, você pode iniciar uma nova Activity (FormActivity) para editar os dados
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra("pessoaId", pessoa.id)
        startActivity(intent)
    }
    override fun showPessoas(pessoas: List<Pessoa>) {
        if(pessoaAdapter!=null){
            pessoaAdapter?.updateData(pessoas)
        }else {
            // Crie o adaptador com a lista de todas as pessoas e o adaptador de endereços
            pessoaAdapter = PessoaAdapter(pessoas, presenterImpl)

            // Defina o layout manager e os adaptadores para o RecyclerViews
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = pessoaAdapter
        }
    }
    private fun exibirMensagem(titulo: String, mensagem: String?) {
        val alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle(titulo)
        alert.setMessage(mensagem)
        alert.setPositiveButton("OK", null)
        alert.show()
    }
    override fun showMensagem(mensagem: String?) {
        exibirMensagem("Sucesso",mensagem)
        filtrarPorNome("")
    }

    override fun showError(erro: String?) {
        exibirMensagem("Ocorreu um erro",erro)
    }
}
