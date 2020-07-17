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
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.longasjulian.form_regis_lb1.ui.main.SectionsPagerAdapterJefe
import kotlinx.android.synthetic.main.activity_main_jefe.*

class MainActivityJefe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_jefe)
        val sectionsPagerAdapter = SectionsPagerAdapterJefe(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        var datosRecibidos = intent.extras
        var nombrein = datosRecibidos?.getString("nombre")
        if(!nombrein.isNullOrEmpty())
            Toast.makeText(this,"Hola! $nombrein", Toast.LENGTH_LONG).show()

        AgregarAnuncios_BT.setOnClickListener {
            startActivity(Intent(this, AgregarAnunciosActivity::class.java))
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflowjefe,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.Cerrar_Sesion){
            FirebaseAuth.getInstance().signOut()
            goToLoginActivity2()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToLoginActivity2() {
        startActivity(Intent(this, LoginActivity2::class.java))
        finish()
    }
}