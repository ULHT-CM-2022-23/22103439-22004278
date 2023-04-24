package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private val adapter = HistoryAdapter(::onOperationClick, App.filmesVistos)
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.bind(view)

        binding.btnRecycler.setOnClickListener { toggleRecyclerView() }
        binding.btnImage.setOnClickListener { toggleImage() }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
    }

    private fun onOperationClick(uuid: String) {
        NavigationManager.goToOperationDetailFragment(parentFragmentManager, uuid)
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