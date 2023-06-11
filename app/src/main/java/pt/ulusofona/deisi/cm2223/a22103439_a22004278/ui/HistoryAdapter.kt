package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.ItemExpressionBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import java.text.SimpleDateFormat
import java.util.*

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
            val dateFromat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            holder.itemView.setOnClickListener { onClick(items[position]) }
            holder.binding.filmeTitulo.text = items[position].filme.nome
            holder.binding.filmeAvaliacao.text = items[position].avalicao.toString()
            holder.binding.filmeData.text = items[position].filme.dataLancamento
            holder.binding.evalData.text = dateFromat.format(items[position].dataVisualizacao)
            holder.binding.cinemaVisto?.text = items[position].cinema.nome
            holder.binding.evalObserv?.text = items[position].observacao

            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(holder.itemView.context).load(items[position].filme.imagemCartaz).into(holder.binding.filmeImagem)
            }
        }

        override fun getItemCount() = items.size

        @SuppressLint("NotifyDataSetChanged")
        fun updateItem(items: List<Avaliacao>){
            this.items = items
            notifyDataSetChanged()
        }
}