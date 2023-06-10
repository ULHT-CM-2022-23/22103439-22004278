package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FilmeVistosItemLayoutBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao

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

        holder.binding.filmeTitulo.text = items[position].filmeIMDB.nomeIMDB
        holder.binding.filmeAvaliacao.text = items[position].avalicaoUtilizador.toString()
        holder.binding.filmeData.text = items[position].filmeIMDB.dataLancamentoIMDB.toString()

        val imagemCartaz = holder.itemView.context.resources.getIdentifier(items[position].filmeIMDB.imagemCartazIMDB, "drawable",  holder.itemView.context.packageName)
        holder.binding.filmeImagem.setImageResource(imagemCartaz)
    }

    override fun getItemCount() = items.size

}
