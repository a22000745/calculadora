package com.example.aulas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulas.databinding.ActivityMainBinding
import com.example.aulas.databinding.ItemExpressionBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private val operations = mutableListOf<String>()
    private val adapter = HistoryAdapter(::onOperationClick)

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var histórico = mutableListOf<String>()
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
        binding.rvHistoric?.layoutManager = LinearLayoutManager(this)
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
        Log.i(TAG,"Click no botão =")
        var expression = ExpressionBuilder(
            binding.textVisor.text.toString()
        ).build()
        operations.add(binding.textVisor.text.toString())
        adapter.updateItems(operations)
        binding.textVisor.text = expression.evaluate().toString()
        Log.i(TAG, "O resultado é ${binding.textVisor.text}")

    }
    fun onOperationClick( operation : String){
        Toast.makeText(this,operation, Toast.LENGTH_LONG).show()
    }
}
class HistoryAdapter(private val onOperationClick: (String) -> Unit,private var items : List<String> = listOf()): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){
    class HistoryViewHolder(val binding :ItemExpressionBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemExpressionBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onOperationClick(items[position])
        }
        val parts = items[position]?.split("=")
        holder.binding.textExpression.text = parts?.get(0)
        holder.binding.textResult.text = parts?.get(1)
    }
    override fun getItemCount() = items.size

    fun updateItems(items: List<String>){
        this.items = items
        notifyDataSetChanged()
    }
}