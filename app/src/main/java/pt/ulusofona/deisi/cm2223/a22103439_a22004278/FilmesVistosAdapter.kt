package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FilmeVistosItemLayoutBinding

class FilmesVistosAdapter(
    private var items: List<Review> = listOf()
) : RecyclerView.Adapter<FilmesVistosAdapter.FilmesVistosViewHolder>()
{

    class FilmesVistosViewHolder(val binding: FilmeVistosItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmesVistosViewHolder {
            return FilmesVistosViewHolder(FilmeVistosItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }

    override fun onBindViewHolder(holder: FilmesVistosViewHolder, position: Int) {
        holder.binding.filmeTitulo.text = items[position].filme.nome
        holder.binding.filmeAvaliacao.text = items[position].avalicao.toString()
        holder.binding.filmeData.text = App.dataAbreviada(items[position].dataVisualizacao)

        val imagemCartaz = holder.itemView.context.resources.getIdentifier(items[position].filme.imagemCartaz, "drawable",  holder.itemView.context.packageName)
        holder.binding.filmeImagem.setImageResource(imagemCartaz)
    }

    override fun getItemCount() = items.size
}