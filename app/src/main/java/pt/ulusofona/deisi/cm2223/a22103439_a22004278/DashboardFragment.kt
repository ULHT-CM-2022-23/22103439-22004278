package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private val adapterVistosLista = FilmesVistosAdapter(App.listaOrdenadaPorDataVisualizacao())
    private val adapterTopLista = FilmesTopAdapter(App.listaOrdenadaPorAvaliacao())

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding = FragmentDashboardBinding.bind(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        binding.filmesVistosLista.layoutManager = LinearLayoutManager(requireContext())
        binding.filmesVistosLista.adapter = adapterVistosLista

        binding.topFilmesLista.layoutManager = LinearLayoutManager(requireContext())
        binding.topFilmesLista.adapter = adapterTopLista
    }
}
