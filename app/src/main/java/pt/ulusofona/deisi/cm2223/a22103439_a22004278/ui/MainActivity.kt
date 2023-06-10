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
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.R
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashboardFragment(supportFragmentManager)
        }

    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()

        binding.btnVoz.setOnClickListener {
            exibirAlertDialog()
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