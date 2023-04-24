package pt.ulusofona.deisi.cm2223.a22103439_a22004278
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object  NavigationManager {

    private fun placeFragment(fm: FragmentManager, fragment: Fragment) {
        val transition = fm.beginTransaction()
        transition.replace(R.id.frame, fragment)
        transition.addToBackStack(null)
        transition.commit()
    }

    fun goToRegistoFragment(fm: FragmentManager) {
        placeFragment(fm, RegistarFilmeFragment())
    }

    fun goToListaFragment(fm: FragmentManager) {
        placeFragment(fm, HistoryFragment())
    }

    fun goToOperationDetailFragment(fm: FragmentManager, uuid: String) {
        placeFragment(fm, DetalheFilmeFragment.newInstance(uuid))
    }

    fun goToDashboardFragment(fm: FragmentManager) {
        placeFragment(fm, DashboardFragment())
    }

}