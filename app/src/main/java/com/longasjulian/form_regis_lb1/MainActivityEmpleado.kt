package com.longasjulian.form_regis_lb1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.longasjulian.form_regis_lb1.ui.main.SectionsPagerAdapter


class MainActivityEmpleado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_empleado)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        var datosRecibidos = intent.extras
        var nombrein = datosRecibidos?.getString("nombre")
        if (!nombrein.isNullOrEmpty())
            Toast.makeText(this, "Hola! $nombrein", Toast.LENGTH_LONG).show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.Cerrar_Sesion) {
            FirebaseAuth.getInstance().signOut()
            goToLoginActivity2()
        }
        if(item.itemId == R.id.Mi_perfil){
            goToMiPerfilActivity()
        }
        if(item.itemId == R.id.Mis_jefes){
            goToMisEMpleadosJefesActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToLoginActivity2() {
        startActivity(Intent(this, LoginActivity2::class.java))
        finish()
    }
    private fun goToMiPerfilActivity() {
        startActivity(Intent(this, MiPerfilActivity::class.java))
    }
    private fun goToMisEMpleadosJefesActivity() {
        startActivity(Intent(this, EmpleadosJefesActivity::class.java))
    }
}
