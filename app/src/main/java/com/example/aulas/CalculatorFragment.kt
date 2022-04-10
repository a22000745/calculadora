package com.example.aulas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aulas.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorFragment : Fragment() {
    private val TAG = MainActivity::class.java.simpleName
    private val operations = mutableListOf<String>()
    private val adapter = HistoryAdapter(::onOperationClick)
    private lateinit var binding: FragmentCalculatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var histórico = mutableListOf<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_calculator, container, false
        )
        binding = FragmentCalculatorBinding.bind(view)
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }
    override fun onStart(){
        super.onStart()
        binding.button1.setOnClickListener{onClickNumber("1") }
        binding.button2.setOnClickListener{onClickNumber("2") }
        binding.button3.setOnClickListener{onClickNumber("3") }
        binding.button4.setOnClickListener{onClickNumber("4") }
        binding.button5.setOnClickListener{onClickNumber("5") }
        binding.button6.setOnClickListener{onClickNumber("6") }
        binding.button7.setOnClickListener{onClickNumber("7") }
        binding.button8.setOnClickListener{onClickNumber("8") }
        binding.button9.setOnClickListener{onClickNumber("9") }
        binding.button0.setOnClickListener{onClickNumber("0") }
        binding.buttonAdition.setOnClickListener{onClickOperand("+")}
        binding.buttonMinus.setOnClickListener{onClickOperand("-")}
        binding.buttonMultiply.setOnClickListener{onClickOperand("*")}
        binding.buttonDevide.setOnClickListener{onClickOperand("/")}
        binding.buttonEquals.setOnClickListener{onClickEquals() }
        binding.buttonClear.setOnClickListener {binding.textVisor.text = "0"}
        binding.rvHistoric?.layoutManager = LinearLayoutManager(this.context)
        binding.rvHistoric?.adapter = adapter
    }
    private fun onClickNumber(number: String){
        Log.i(TAG, "Click no botão $number")
        if(binding.textVisor.text == "0"){
            binding.textVisor.text = number
        }else{
            binding.textVisor.append(number)
        }
    }
    private fun onClickOperand(symbol: String){
        Log.i(TAG, "Click no botão $symbol")
        binding.textVisor.append(symbol)
    }
    private fun onClickEquals(){
        var resultado = "${binding.textVisor.text}="
        Log.i(TAG,"Click no botão =")
        var expression = ExpressionBuilder(
            binding.textVisor.text.toString()
        ).build()
        binding.textVisor.text = expression.evaluate().toString()
        resultado += binding.textVisor.text
        operations.add(resultado)
        adapter.updateItems(operations)
        Log.i(TAG, "O resultado é ${binding.textVisor.text}")

    }
    fun onOperationClick( operation : String){
        Toast.makeText(this.context,operation, Toast.LENGTH_LONG).show()
    }
}