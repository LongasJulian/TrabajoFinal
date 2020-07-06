package com.longasjulian.form_regis_lb1

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.longasjulian.form_regis_lb1.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        var datosRecibidos = intent.extras

        // val Nombre           = datosRecibidos?.getString("nombre")
        val Correo           = datosRecibidos?.getString("correo")
        val Contrasena_IN       = datosRecibidos?.getString("Contrasena")

        Nombre_aMain_TV.text = datosRecibidos?.getString("nombre").toString()
        Correo_aMain_TV.text = Correo.toString()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var datosRecibidos = intent.extras
        val Correo    =     Correo_aMain_TV.text.toString()
        val Nombre    =      Nombre_aMain_TV.text.toString()
        if(item.itemId == R.id.Cerrar_Sesion){
            val intent= Intent(this, LoginActivity2::class.java)
            intent.putExtra("Contrasena", (datosRecibidos?.getString("contrasena")).toString())
            intent.putExtra("Correo", Correo.toString())
            intent.putExtra("Nombre", Nombre.toString())
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}