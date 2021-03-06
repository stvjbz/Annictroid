package me.kirimin.annictroid.top

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem

import me.kirimin.annictroid.auth.AuthActivity

import kotlinx.android.synthetic.main.activity_top.*
import me.kirimin.annictroid.R
import me.kirimin.annictroid._common.preferences.AppPreferences
import me.kirimin.annictroid.program.ProgramListFragment
import me.kirimin.annictroid.myanime.MyAnimeListFragment
import me.kirimin.annictroid.settings.SettingsActivity

class TopActivity : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        setSupportActionBar(toolbar)
        if (AppPreferences.getToken(this) == "") {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(drawerToggle)
        val adapter = TopPagerAdapter(supportFragmentManager)
        adapter.addPage(ProgramListFragment(), "放送予定")
        adapter.addPage(MyAnimeListFragment(), "見てるアニメ")
        viewPager.adapter = adapter
        pagerTab.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        pagerTab.setViewPager(viewPager)
        navigationButtonHome.setOnClickListener { drawerLayout.closeDrawers() }
        navigationButtonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            drawerLayout.closeDrawers()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }
}
