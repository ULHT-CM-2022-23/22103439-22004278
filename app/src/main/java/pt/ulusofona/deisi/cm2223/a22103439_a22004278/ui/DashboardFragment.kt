package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.Repository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentDashboardBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Operations

class DashboardFragment : Fragment() {

    private var adapterTopLista: FilmesTopAdapter = FilmesTopAdapter(listOf())
    private val model: Operations = Repository.getInstance()

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding = FragmentDashboardBinding.bind(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            model.getTop5Avaliacoes(false) {
                CoroutineScope(Dispatchers.Main).launch {
                    adapterTopLista.updateItem(it.getOrDefault(listOf()))
                    binding.topFilmesLista.layoutManager = LinearLayoutManager(requireContext())
                    binding.topFilmesLista.adapter = adapterTopLista
                }
            }
            model.getMediaAvaliacoes { media ->
                CoroutineScope(Dispatchers.Main).launch {
                    val mediaFormat = String.format("%.2f", media.getOrDefault(0.0))
                    binding.ratingsMean.text = mediaFormat
                }
            }
            model.getCountAvaliacoes { count ->
                CoroutineScope(Dispatchers.Main).launch {
                    binding.ratingsCount.text = count.getOrDefault(0).toString()
                }
            }
        }
    }
}
