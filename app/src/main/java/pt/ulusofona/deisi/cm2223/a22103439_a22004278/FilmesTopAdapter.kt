package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FilmeVistosItemLayoutBinding

class FilmesTopAdapter (
    private var items: List<Filme> = listOf()
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

        holder.binding.filmeTitulo.text = items[position].nome
        holder.binding.filmeAvaliacao.text = items[position].avaliacao.toString()
        holder.binding.filmeData.text = App.dataAbreviada(items[position].dataLancamento)

        val imagemCartaz = holder.itemView.context.resources.getIdentifier(items[position].imagemCartaz, "drawable",  holder.itemView.context.packageName)
        holder.binding.filmeImagem.setImageResource(imagemCartaz)
    }

    override fun getItemCount() = items.size

}
