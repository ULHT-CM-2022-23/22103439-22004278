package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.App
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentDetalheFilmeBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Review

private const val ARG_OPERATION_UUID = "ARG_OPERATION_UUID"

class DetalheFilmeFragment : Fragment() {

    private lateinit var binding: FragmentDetalheFilmeBinding
    private var operationUuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {operationUuid = it.getString(ARG_OPERATION_UUID)}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detalhe_filme, container, false)
        binding = FragmentDetalheFilmeBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        operationUuid?.let { uuid ->
            val operation = App.getFilmeById(uuid)
            operation?.let { placeData(it) }
        }
    }

    private fun placeData(ui: Review) {
        binding.rvImagens.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvImagens.adapter = ImagemDetalheAdapter(ui.getImages())


        binding.movieTitle.text = ui.filme.nome
        binding.movieGenero.text = ui.filme.genero
        binding.movieRating.text = ui.avalicao.toString()
        binding.movieAvaliacaoIMDB.text = ui.filme.avaliacao.toString()
        binding.movieDataLancamento.text = App.dataAbreviada(ui.filme.dataLancamento)
        binding.movieSinopse.text = ui.filme.sinopse
        binding.movieCinema.text = ui.cinema.cinema_name
        binding.movieViewedDate.text = App.dataAbreviada(ui.dataVisualizacao)
        binding.movieDescicao.text = ui.observacao.toString()
        binding.imdbButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ui.filme.link))
            startActivity(intent)
        }

        val imagemCartaz = context?.resources?.getIdentifier(ui.filme.imagemCartaz, "drawable",  context?.packageName)
        if (imagemCartaz != null) {
            binding.posterImage.setImageResource(imagemCartaz)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(uuid: String) =
            DetalheFilmeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_OPERATION_UUID, uuid)
                }
            }
    }
}