package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.ItemExpressionBinding

class HistoryAdapter (
    private val onClick: (String) -> Unit,
    private var items: List<Review> = listOf()
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
            holder.itemView.setOnClickListener { onClick(items[position].id) }
            holder.binding.textExpression.text = items[position].filme.nome
            holder.binding.textResult.text = items[position].avalicao.toString()
            holder.binding.textObs?.text = items[position].observacao
        }

        override fun getItemCount() = items.size

}