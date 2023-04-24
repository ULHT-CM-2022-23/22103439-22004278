package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.ImagemFilmeDetalheBinding
import java.io.File

class ImagemDetalheAdapter(
    private var items: List<File> = listOf()
) : RecyclerView.Adapter<ImagemDetalheAdapter.ImagemDetalheViewHolder>() {
    class ImagemDetalheViewHolder(val binding: ImagemFilmeDetalheBinding) :  RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagemDetalheViewHolder {
        return ImagemDetalheViewHolder(
            ImagemFilmeDetalheBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImagemDetalheViewHolder, position: Int) {

        holder.binding.apply {
            listaFilmeImage.setImageURI(items[position].toUri())
        }
    }

    override fun getItemCount() = items.size

}