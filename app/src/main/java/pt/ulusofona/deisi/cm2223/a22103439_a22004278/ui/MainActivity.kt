package pt.ulusofona.deisi.cm2223.a22103439_a22004278.ui
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.fondesa.kpermissions.extension.permissionsBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.Repository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.ActivityMainBinding
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Operations

import android.Manifest
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send

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

    private fun exibirAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.voice_search)
        builder.setMessage(R.string.dialog_message)

        val textView = TextView(this)
        textView.gravity = Gravity.CENTER
        textView.textSize = 24f
        builder.setView(textView)

        val alertDialog = builder.create()
        alertDialog.show()

        // Iniciar contagem regressiva
        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView.text = "${millisUntilFinished / 1000}"
            }
            override fun onFinish() {
                alertDialog.dismiss()
            }
        }.start()
    }
}