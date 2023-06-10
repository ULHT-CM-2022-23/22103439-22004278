package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FilmeVistosItemLayoutBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao

class FilmesVistosAdapter(
    private var items: List<Avaliacao> = listOf()
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
        holder.binding.filmeTitulo.text = items[position].filmeIMDB.nome
        holder.binding.filmeAvaliacao.text = items[position].avalicaoUtilizador.toString()
        holder.binding.filmeData.text = items[position].dataVisualizacaoUtilizador.toString()

        val imagemCartaz = holder.itemView.context.resources.getIdentifier(items[position].filmeIMDB.imagemCartaz, "drawable",  holder.itemView.context.packageName)
        holder.binding.filmeImagem.setImageResource(imagemCartaz)
    }

    override fun getItemCount() = items.size
}