package com.basis.test.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.basis.test.R
import com.basis.test.databinding.ItemPessoaBinding
import com.basis.test.model.Pessoa
import com.basis.test.presenter.MainPresenterImpl
import com.basis.test.view.FormActivity
import com.basis.test.view.MainActivity
import io.realm.RealmResults
import java.lang.StringBuilder

class PessoaAdapter(
    private var pessoas: List<Pessoa>,
    private val presenterImpl: MainPresenterImpl
) : RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        var itemPessoaBinding = ItemPessoaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PessoaViewHolder(itemPessoaBinding, parent.context, presenterImpl)
    }

    override fun onBindViewHolder(holder: PessoaViewHolder, position: Int) {
        if (position < pessoas.size) {
            val pessoa = pessoas[position]
            pessoa?.let {
                holder.bind(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return pessoas.size
    }

    fun updateData(pessoas: List<Pessoa>) {
        this.pessoas = pessoas
        notifyDataSetChanged()
    }

    inner class PessoaViewHolder(val itemBinding: ItemPessoaBinding,val context: Context, val presenterImpl: MainPresenterImpl) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(pessoa: Pessoa) {

            if (pessoa.tipoPessoa == "fisica") {
                itemBinding.textNome.text = pessoa.nome
                itemBinding.textDocumento.text = Html.fromHtml("<b>CPF:</b> ${pessoa.cpf}",Html.FROM_HTML_MODE_LEGACY)
            } else {
                itemBinding.textNome.text = pessoa.razaoSocial
                itemBinding.textDocumento.text = Html.fromHtml("<b>CNPJ:</b> ${pessoa.cnpj}",Html.FROM_HTML_MODE_LEGACY)
            }
            itemBinding.textTelefone.text = Html.fromHtml("<b>Tel:</b> ${pessoa.dddTelefone}",Html.FROM_HTML_MODE_LEGACY)
            itemBinding.textEmail.text = Html.fromHtml("<b>Email:</b> ${pessoa.email ?: "não informado"}",Html.FROM_HTML_MODE_LEGACY)

            val enderecosResidenciais = obterListaEnderecoFormatada("residencial", pessoa)
            if(!enderecosResidenciais.isNullOrEmpty()) {
                itemBinding.textEnderecoResidencial.visibility = View.VISIBLE
                itemBinding.textEnderecoResidencial.text = Html.fromHtml(
                    "<b>Endereço Residencial:\n</b> ${enderecosResidenciais}",
                    Html.FROM_HTML_MODE_LEGACY
                )
            }

            val enderecosComerciais = obterListaEnderecoFormatada("comercial", pessoa)
            if(!enderecosComerciais.isNullOrEmpty()) {
                itemBinding.textEnderecoComercial.visibility = View.VISIBLE
                itemBinding.textEnderecoComercial.text = Html.fromHtml(
                    "<b>Endereço Comercial:\n</b> ${enderecosComerciais}",
                    Html.FROM_HTML_MODE_LEGACY
                )
            }
            itemBinding.buttonEditar.setOnClickListener {
                val intent = Intent(context, FormActivity::class.java)
                intent.putExtra("pessoa_id", pessoa.id)
                startActivity(context, intent, null)
            }

            itemBinding.buttonExcluir.setOnClickListener {
                exclusaoDialog(pessoa)
            }
        }

        private fun obterListaEnderecoFormatada(tipoEndereco:String, pessoa: Pessoa):String{
            var stringEndereco = StringBuilder()
            pessoa.enderecos?.filter { i -> i.tipoEndereco==tipoEndereco }
                ?.forEach {e -> stringEndereco.append(e.getEnderecoFormatado()+"\n") }

            return stringEndereco.toString()
        }
        private fun exclusaoDialog(pessoa: Pessoa){
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Deseja realmente excluir o cadastro?")
                .setPositiveButton("Confirmar"
                ) { dialog, id ->
                    presenterImpl.excluirPessoa(pessoa)
                    notifyDataSetChanged()
                }
                .setNegativeButton("Cancelar"
                ) { dialog, id ->
                    dialog.dismiss()
                }
            builder.create().show()
        }
    }
}
