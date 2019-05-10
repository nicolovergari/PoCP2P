package com.company.carlo.p2pcarsharing


import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.amazonaws.auth.AWSCredentialsProvider
import kotlinx.android.synthetic.main.activity_home.*
import com.amazonaws.mobile.config.AWSConfiguration;

class HomeActivity : AppCompatActivity() {

    private val homeFragment: HomeFragment = HomeFragment()
    private val dashboardFragment: DashboardFragment = DashboardFragment()
    private val myCarFragment: myCarFragment = myCarFragment()

    private val notificationFragment: NotificationFragment = NotificationFragment()
    private val notLoggedFragment: NotLoggedFragment= NotLoggedFragment()
    private var isLogged : Boolean = false

    private fun changeFragment (newFragment : Fragment) {
        if(!newFragment.isVisible) {
            val transaction = supportFragmentManager.beginTransaction()
            if(!isLogged && newFragment!=dashboardFragment )
            {
                transaction.replace(R.id.fragment_container, notLoggedFragment)
            }
            else {
                transaction.replace(R.id.fragment_container, newFragment)
            }
            transaction.commit()
        }
        return
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
            item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                changeFragment(homeFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                changeFragment(dashboardFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mycar -> {
                changeFragment(myCarFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                changeFragment(notificationFragment)
                return@OnNavigationItemSelectedListener true            }
        }
        false
    }

    

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if( intent.extras != null ) {
            val bundle: Bundle = intent.extras
            isLogged = bundle.getBoolean("logged")
            changeFragment(homeFragment)
        }else{
            changeFragment(homeFragment)
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        buttoninhome.setOnClickListener {
            if( isLogged ){
                isLogged = false
                buttoninhome.text = "Login"
                changeFragment(notLoggedFragment)
                navigation.setSelectedItemId(R.id.navigation_home)
            }
            else{
                isLogged = true
                buttoninhome.text = "Logout"
                changeFragment(homeFragment)
                navigation.setSelectedItemId(R.id.navigation_home)
            }
        }




    }



    
}
