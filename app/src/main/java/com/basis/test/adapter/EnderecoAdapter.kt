package com.basis.test.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.basis.test.R
import com.basis.test.databinding.ItemEnderecoBinding
import com.basis.test.databinding.ItemPessoaBinding
import com.basis.test.model.Endereco

class EnderecoAdapter(private var enderecos: List<Endereco>) :
    RecyclerView.Adapter<EnderecoAdapter.EnderecoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnderecoViewHolder {
        var itemEnderecoBinding = ItemEnderecoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EnderecoViewHolder(itemEnderecoBinding)
    }

    override fun onBindViewHolder(holder: EnderecoViewHolder, position: Int) {
        val endereco = enderecos[position]
        holder.bind(endereco)
    }

    fun updateData(enderecos: List<Endereco>) {
        this.enderecos = enderecos
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return enderecos.size
    }

    // Classe ViewHolder
    class EnderecoViewHolder(val binding: ItemEnderecoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(endereco: Endereco) {
            binding.textTipoEndereco.text = Html.fromHtml("<b>Tipo:</b> ${endereco.tipoEndereco}", Html.FROM_HTML_MODE_LEGACY)
            binding.textEndereco.text =Html.fromHtml("<b>Endereço:</b> ${endereco.endereco}",Html.FROM_HTML_MODE_LEGACY)
            binding.textNumeroComplemento.text = Html.fromHtml("<b>Número:</b> ${endereco.numero ?: "não informado"} <b>Complemento:</b> ${if(endereco.complemento.isNullOrEmpty())"não informado" else endereco.complemento}",Html.FROM_HTML_MODE_LEGACY)
            binding.textBairroCep.text = Html.fromHtml("<b>Bairro:</b> ${endereco.bairro} <b>CEP:</b> ${endereco.cep}",Html.FROM_HTML_MODE_LEGACY)
            binding.textCidadeUf.text = Html.fromHtml("<b>${"${endereco.cidade?.uppercase()}/${endereco.uf?.uppercase()}"}</b>", Html.FROM_HTML_MODE_LEGACY)
        }
    }
}
