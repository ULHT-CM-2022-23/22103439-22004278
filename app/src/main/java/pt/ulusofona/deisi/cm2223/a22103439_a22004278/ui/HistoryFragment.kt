package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.annotation.SuppressLint
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
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.IMDBRepository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentHistoryBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao

class HistoryFragment : Fragment() {

    private val adapter = HistoryAdapter(::onOperationClick)
    private lateinit var binding: FragmentHistoryBinding
    private val model = IMDBRepository.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
                val view = inflater.inflate(R.layout.fragment_history, container, false)
                binding = FragmentHistoryBinding.bind(view)
                binding.btnRecycler.setOnClickListener { toggleRecyclerView() }
                binding.btnImage.setOnClickListener { toggleImage() }

                return binding.root
             }

    @SuppressLint("SuspiciousIndentation")
    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            var listaAvaliacoes : List<Avaliacao> = listOf()
                model.getAllAvaliacoes {
                    listaAvaliacoes = it.getOrNull()!!
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
                        binding.rvHistory.adapter = adapter
                        adapter.updateItem(listaAvaliacoes)
                    }
                }

        }
    }

    private fun onOperationClick(avaliacao: Avaliacao) {
        NavigationManager.goToOperationDetailFragment(parentFragmentManager, avaliacao.idUtilizador)
    }

    private fun toggleRecyclerView() {
        if (binding.rvHistory.visibility == View.VISIBLE) {
            binding.rvHistory.visibility = View.GONE
        } else {
            binding.rvHistory.visibility = View.VISIBLE
            binding.imageView.visibility = View.GONE
        }
    }

    private fun toggleImage() {
        if (binding.imageView.visibility == View.VISIBLE) {
            binding.imageView.visibility = View.GONE
        } else {
            binding.imageView.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.GONE
        }
    }
}