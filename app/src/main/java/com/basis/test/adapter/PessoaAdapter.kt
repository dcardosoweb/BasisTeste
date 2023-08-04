package com.basis.test.adapter

import android.content.Context
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
import com.basis.test.view.FormActivity
import com.basis.test.view.MainActivity
import io.realm.RealmResults

class PessoaAdapter(
    private var pessoas: List<Pessoa>,
    private val enderecoAdapter: EnderecoAdapter,
    private val onPessoaLongClickListener: MainActivity
) : RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        var itemPessoaBinding = ItemPessoaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PessoaViewHolder(itemPessoaBinding, parent.context)
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

    inner class PessoaViewHolder(val itemBinding: ItemPessoaBinding,val context: Context) : RecyclerView.ViewHolder(itemBinding.root) {
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

            // Configurar o RecyclerView de endereços usando o EnderecoAdapter
            pessoa.enderecos?.let { enderecoAdapter.updateData(it) }

            itemBinding.buttonEditar.setOnClickListener {
                val intent = Intent(context, FormActivity::class.java)
                intent.putExtra("pessoa_id", pessoa.id)
                startActivity(context, intent, null)
            }
        }

        init {
            // Configurar o clique longo na view do item
            itemView.setOnLongClickListener {
                val pessoa = pessoas[adapterPosition]
                if (pessoa != null) {
                    if (pessoa.isValid) {
                        onPessoaLongClickListener.onPessoaLongClick(pessoa)
                    }
                }
                true
            }
        }
    }
}
