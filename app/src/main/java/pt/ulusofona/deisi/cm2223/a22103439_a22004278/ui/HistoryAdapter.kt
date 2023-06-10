package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.ItemExpressionBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao

class HistoryAdapter (
    private val onClick: (Avaliacao) -> Unit,
    private var items: List<Avaliacao> = listOf()
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>()
{
    class HistoryViewHolder(val binding: ItemExpressionBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
            return HistoryViewHolder(ItemExpressionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ))
        }

        override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
            holder.itemView.setOnClickListener { onClick(items[position]) }
            holder.binding.textExpression.text = items[position].filmeIMDB.nomeIMDB
            holder.binding.textResult.text = items[position].avalicaoUtilizador.toString()
            holder.binding.textObs?.text = items[position].observacaoUtilizador
        }

        override fun getItemCount() = items.size

        fun updateItem(items: List<Avaliacao>){
            this.items = items
            notifyDataSetChanged()
        }
}