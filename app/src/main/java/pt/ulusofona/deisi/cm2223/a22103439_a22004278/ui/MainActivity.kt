package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.Repository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.ActivityMainBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Operations
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: Operations
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = Repository.getInstance()

        if(!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashboardFragment(supportFragmentManager)
        }
        // Só vamos abrir a aplicação se aceitar as permissões da localização
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION).build().send { result ->
            if (result.allGranted()) {
                // Este if já cá estava antes, para garantir que ficamos no
                // ecrã em caso de ocorrer uma rotação
                if(!screenRotated(savedInstanceState)) {
                    NavigationManager.goToDashboardFragment(supportFragmentManager)
                }
            } else {
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()

        binding.btnVoz.setOnClickListener {
            exibirAlertDialog()
        }

        CoroutineScope(Dispatchers.IO).launch{
            model.getCinemaJSON { it.getOrNull() }
        }
    }

    private fun screenRotated(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState != null
    }

    private fun setupDrawerMenu() {
        val toggle = ActionBarDrawerToggle(this,
            binding.drawer, binding.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        binding.navDrawer.setNavigationItemSelectedListener{
            onClickNavigationItem(it)
        }
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun onClickNavigationItem(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_registo ->
                NavigationManager.goToRegistoFragment(
                    supportFragmentManager
                )

            R.id.nav_lista ->
                NavigationManager.goToListaFragment(
                    supportFragmentManager
                )

            R.id.nav_dashboard ->
                NavigationManager.goToDashboardFragment(
                    supportFragmentManager
                )

            R.id.nav_map ->
                NavigationManager.goToMapFragment(supportFragmentManager)

        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private val REQUEST_CODE_SPEECH_INPUT = 0

    @SuppressLint("MissingInflatedId")
    private fun exibirAlertDialog() {
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.voice_search, null)
        val messageTextView = view.findViewById<TextView>(R.id.voice_search_result)
        val searchButton = view.findViewById<Button>(R.id.searchButton)
        val voiceButton = view.findViewById<Button>(R.id.voiceButton)

        val alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .create()
        alertDialog.show()
        iniciarPesquisaPorVoz {
            messageTextView.text = it
        }

        searchButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                model.getFilmeByName(messageTextView.text.toString()) {
                    it.onSuccess { filme ->
                        model.getAvaliacaoByFilme(filme.id) {
                            it.onSuccess { avaliacao ->
                                CoroutineScope(Dispatchers.Main).launch {
                                    alertDialog.dismiss()
                                    NavigationManager.goToOperationDetailFragment(supportFragmentManager, avaliacao.id)
                                }
                            }
                            it.onFailure {
                                CoroutineScope(Dispatchers.Main).launch {
                                    Toast.makeText(this@MainActivity, getString(R.string.no_results), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }

        voiceButton.setOnClickListener {
            // Ação do botão para iniciar a pesquisa de voz
            iniciarPesquisaPorVoz {
                messageTextView.text = it
            }
        }
    }

    private val speechRecognitionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                val resultRecognizer = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (!resultRecognizer.isNullOrEmpty()) {
                    voiceRecognitionCallback.invoke(resultRecognizer[0])
                }
            }
        }
    }

    private lateinit var voiceRecognitionCallback: (String) -> Unit

    private fun iniciarPesquisaPorVoz(callback: (String) -> Unit) {
        voiceRecognitionCallback = callback

        val permission = Manifest.permission.RECORD_AUDIO
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_CODE_SPEECH_INPUT)
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

            try {
                speechRecognitionLauncher.launch(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.voice_search_error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}