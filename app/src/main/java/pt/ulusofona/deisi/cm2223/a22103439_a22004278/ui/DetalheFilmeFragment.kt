package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentDetalheFilmeBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.IMDB


private const val ARG_OPERATION_UUID = "ARG_OPERATION_UUID"

class DetalheFilmeFragment : Fragment() {

    private lateinit var model: IMDB
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
 /*           val operation = App.getFilmeById(uuid)
            operation?.let { placeData(it) }*/
            CoroutineScope(Dispatchers.IO).launch {
                var avaliacao : Avaliacao
                model.getAvaliacao(uuid) {
                    avaliacao = it.getOrNull()!!
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.rvImagens.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        //binding.rvImagens.adapter = ImagemDetalheAdapter(ui.getImages())
                        binding.movieTitle.text =  avaliacao.filmeIMDB.nomeIMDB
                        binding.movieGenero.text = avaliacao.filmeIMDB.generoIMDB
                        binding.movieRating.text = avaliacao.avalicaoUtilizador.toString()
                        binding.movieAvaliacaoIMDB.text = avaliacao.filmeIMDB.avaliacaoIMDB
                        binding.movieDataLancamento.text = avaliacao.filmeIMDB.dataLancamentoIMDB
                        binding.movieSinopse.text = avaliacao.filmeIMDB.sinopseIMDB
                        binding.movieCinema.text = avaliacao.cinemaJSON?.cinema_name
                        binding.movieViewedDate.text = avaliacao.dataVisualizacaoUtilizador.toString()
                        binding.movieDescicao.text = avaliacao.observacaoUtilizador
                        binding.imdbButton.setOnClickListener{
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(avaliacao.filmeIMDB.linkIMDB))
                            startActivity(intent)
                        }

                        val imagemCartaz = context?.resources?.getIdentifier(avaliacao.filmeIMDB.imagemCartazIMDB, "drawable",  context?.packageName)
                        if (imagemCartaz != null) {
                            binding.posterImage.setImageResource(imagemCartaz)
                        }
                    }
                }

            }
        }
    }

    private fun placeData(ui: Avaliacao) {
        binding.rvImagens.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //binding.rvImagens.adapter = ImagemDetalheAdapter(ui.getImages())
        binding.movieTitle.text = ui.filmeIMDB.nomeIMDB
        binding.movieGenero.text = ui.filmeIMDB.generoIMDB
        binding.movieRating.text = ui.avalicaoUtilizador.toString()
        binding.movieAvaliacaoIMDB.text = ui.filmeIMDB.avaliacaoIMDB
        binding.movieDataLancamento.text = ui.filmeIMDB.dataLancamentoIMDB
        binding.movieSinopse.text = ui.filmeIMDB.sinopseIMDB
        binding.movieCinema.text = ui.cinemaJSON?.cinema_name
        binding.movieViewedDate.text = ui.dataVisualizacaoUtilizador.toString()
        binding.movieDescicao.text = ui.observacaoUtilizador
        binding.imdbButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ui.filmeIMDB.linkIMDB))
            startActivity(intent)
        }

        val imagemCartaz = context?.resources?.getIdentifier(ui.filmeIMDB.imagemCartazIMDB, "drawable",  context?.packageName)
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