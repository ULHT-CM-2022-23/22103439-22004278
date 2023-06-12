package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FilmeVistosItemLayoutBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import java.text.SimpleDateFormat
import java.util.*

class FilmesTopAdapter (
    private var items: List<Avaliacao> = listOf()
) : RecyclerView.Adapter<FilmesTopAdapter.FilmesTopViewHolder>()
{

    class FilmesTopViewHolder(val binding: FilmeVistosItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmesTopViewHolder {
        return FilmesTopViewHolder(
            FilmeVistosItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ))
    }

    override fun onBindViewHolder(holder: FilmesTopViewHolder, position: Int) {
        val dateFromat = SimpleDateFormat("yyy/MM/dd", Locale.getDefault())

        holder.binding.filmeTitulo.text = items[position].filme.nome
        holder.binding.filmeAvaliacao.text = items[position].avalicao.toString()
        holder.binding.filmeData.text = items[position].filme.dataLancamento
        holder.binding.evalData.text = dateFromat.format(items[position].dataVisualizacao)

        CoroutineScope(Dispatchers.Main).launch {
            Glide.with(holder.itemView.context).load(items[position].filme.imagemCartaz).into(holder.binding.filmeImagem)
        }
    }

    override fun getItemCount() = items.size

    fun updateItem(items: List<Avaliacao>){
        this.items = items
        notifyDataSetChanged()
    }

}
