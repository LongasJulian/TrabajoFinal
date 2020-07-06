package com.longasjulian.form_regis_lb1.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.longasjulian.form_regis_lb1.Fragmentos.AnunciosFragment
import com.longasjulian.form_regis_lb1.Fragmentos.HorarioFragment
import com.longasjulian.form_regis_lb1.Fragmentos.IngresoFragment
import com.longasjulian.form_regis_lb1.R

private val TAB_TITLES = arrayOf(
    R.string.Anuncios_fragment_label,
    R.string.horarios_fragment_label,
    R.string.Ingreso_fragment_label
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        when(position){
            0 -> return AnunciosFragment()
            1 -> return HorarioFragment()
            else -> return IngresoFragment()
        }
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}