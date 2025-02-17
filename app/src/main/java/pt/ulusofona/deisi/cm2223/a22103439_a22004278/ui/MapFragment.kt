package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.Repository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.FragmentMapBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.FusedLocation
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.OnLocationChangedListener
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Operations

class MapFragment : Fragment(), OnLocationChangedListener {
    private lateinit var model: Operations

    private lateinit var binding: FragmentMapBinding

    // Esta variável irá guardar uma referência para o mapa
    private var map: GoogleMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = Repository.getInstance()

        val view = inflater.inflate(R.layout.fragment_map, container, false)
        binding = FragmentMapBinding.bind(view)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync { map ->
            this.map = map
            FusedLocation.registerListener(this)
            // Coloca um ponto azul no mapa com a localização do utilizador
            map.isMyLocationEnabled = true


            CoroutineScope(Dispatchers.IO).launch {
                model.getAllAvaliacoes {
                    it.onSuccess {
                        val avaliacoes: List<Avaliacao> = it
                        CoroutineScope(Dispatchers.Main).launch {
                            for (avaliacao in avaliacoes) {
                                val markerOptions = MarkerOptions()
                                    .position(
                                        LatLng(
                                            avaliacao.cinema.latitude,
                                            avaliacao.cinema.longitude
                                        )
                                    )
                                    .title(avaliacao.filme.nome)
                                    .snippet("Grau de satisfação: ${avaliacao.avalicao}")
                                    .icon(getMarkerIcon(avaliacao.avalicao))

                                map.addMarker(markerOptions)
                            }
                        }
                    }
                }
            }

            map.setOnMarkerClickListener { marker ->
                // Recupere as informações do filme associadas ao marcador e navegue para a tela de detalhes
                CoroutineScope(Dispatchers.IO).launch {
                    model.getAllAvaliacoes {
                        it.onSuccess {
                            val avaliacoes: List<Avaliacao> = it
                            CoroutineScope(Dispatchers.Main).launch {
                                for (avaliacao in avaliacoes) {
                                    if (avaliacao.filme.nome == marker.title) {
                                        NavigationManager.goToOperationDetailFragment(
                                            parentFragmentManager,
                                            avaliacao.id
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                true
            }

        }
        return binding.root
    }

    // Este método será invocado sempre que a posição alterar
    override fun onLocationChanged(latitude: Double, longitude: Double) {
        placeCamera(latitude, longitude)
    }

    // Atualiza e faz zoom no mapa de acordo com a localização
    private fun placeCamera(latitude: Double, longitude: Double) {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(latitude, longitude))
            .zoom(12f)
            .build()

        map?.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    // Se o fragmento do mapa for destruído queremos parar de receber a
    // localização, se não podemos ter uma NullPointerException
    override fun onDestroy() {
        super.onDestroy()
        FusedLocation.unregisterListener()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    fun getMarkerIcon(grauSatisfacao: Int): BitmapDescriptor {
        return when (grauSatisfacao) {
            in 1..2 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            in 3..4 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
            in 5..6 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
            in 7..8 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            in 9..10 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            else -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        }
    }
}