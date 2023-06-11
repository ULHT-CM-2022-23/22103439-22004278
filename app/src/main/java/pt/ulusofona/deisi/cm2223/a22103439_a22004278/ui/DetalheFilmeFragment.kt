package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.Repository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentDetalheFilmeBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_OPERATION_UUID = "ARG_OPERATION_UUID"

class DetalheFilmeFragment : Fragment() {

    private val model = Repository.getInstance()
    private lateinit var binding: FragmentDetalheFilmeBinding
    private var operationUuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {operationUuid = it.getString(ARG_OPERATION_UUID)}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_detalhe_filme, container, false)
        binding = FragmentDetalheFilmeBinding.bind(view)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onStart() {
        super.onStart()
        val dateFromat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        operationUuid?.let { uuid ->
            CoroutineScope(Dispatchers.IO).launch {
                var avaliacao : Avaliacao
                model.getAvaliacao(uuid) {
                    avaliacao = it.getOrNull()!!
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.rvImagens.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding.movieTitle.text =  avaliacao.filme.nome
                        binding.movieGenero.text = avaliacao.filme.genero
                        binding.movieRating.text = avaliacao.avalicao.toString()
                        binding.movieAvaliacaoIMDB.text = avaliacao.filme.avaliacao
                        binding.movieDataLancamento.text = avaliacao.filme.dataLancamento
                        binding.movieSinopse.text = avaliacao.filme.sinopse
                        binding.movieLanguages.text = avaliacao.filme.idiomas
                        binding.movieCinema.text = avaliacao.cinema.nome
                        binding.movieCinemaAddress.text = avaliacao.cinema.morada
                        binding.movieCinemaCounty.text = avaliacao.cinema.localidade
                        binding.movieViewedDate.text = dateFromat.format(avaliacao.dataVisualizacao)
                        binding.movieDescicao.text = if(avaliacao.observacao == "") "N/A" else avaliacao.observacao
                        binding.imdbButton.setOnClickListener{
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(avaliacao.filme.link))
                            startActivity(intent)
                        }

                        Glide.with(requireContext())
                            .load(avaliacao.filme.imagemCartaz)
                            .into(binding.posterImage)
                    }

                    model.getCinemaRating(avaliacao.cinema.id) {
                        it.onSuccess { ratings ->
                            CoroutineScope(Dispatchers.Main).launch {
                                for (i in ratings.indices) {
                                    binding.cinemaRatingCat1.text = ratings[0].categoria
                                    binding.cinemaRatingCat2.text = ratings[1].categoria
                                    binding.cinemaRatingCat3.text = ratings[2].categoria
                                    binding.cinemaRatingCat4.text = ratings[3].categoria
                                    binding.cinemaRatingCat1Value.text = ratings[0].score.toString()
                                    binding.cinemaRatingCat2Value.text = ratings[1].score.toString()
                                    binding.cinemaRatingCat3Value.text = ratings[2].score.toString()
                                    binding.cinemaRatingCat4Value.text = ratings[3].score.toString()
                                }
                            }
                        }
                    }
                }
            }
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