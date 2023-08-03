package com.basis.test.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.basis.test.databinding.ActivityFormBinding
import com.basis.test.utils.MaskWatcher

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupView()
    }

    private fun setupView(){
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

        binding.btnSalvar.setOnClickListener {
            exibirMensagem("Dados Invalidos", "Motivo x\nMotivo y\nMotivo z")
        }

        binding.editTextCpf.addTextChangedListener { MaskWatcher.buildCpf() }
        binding.editTextCnpj.addTextChangedListener { MaskWatcher.buildCnpj() }
    }

    private fun validarForm(){

    }

    private fun exibirMensagem(titulo:String, menssagem:String){
        val alert =AlertDialog.Builder(this@FormActivity)
        alert.setTitle(titulo)
        alert.setMessage(menssagem)
        alert.setPositiveButton("OK", null)
        alert.show()
    }
}