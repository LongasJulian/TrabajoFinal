package com.longasjulian.form_regis_lb1.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.longasjulian.form_regis_lb1.Fragmentos.*
import com.longasjulian.form_regis_lb1.R


private val TAB_TITLES = arrayOf(
    R.string.Anuncios_Jefe_fragment_label,
    R.string.horarios_Jefe_fragment_label,
    R.string.Empleados_fragment_label
)

class SectionsPagerAdapterJefe(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        when(position){
            0 -> return AnunciosJefeFragment()
            1 -> return HorarioJefeFragment()
            else -> return EmpleadosFragment()
        }
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
    }