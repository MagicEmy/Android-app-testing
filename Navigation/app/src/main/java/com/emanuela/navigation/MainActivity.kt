package com.emanuela.navigation

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentContainerView
import com.emanuela.navigation.ui.theme.NavigationTheme
import com.tomtom.sdk.datamanagement.navigationtile.NavigationTileStore
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.ui.MapFragment
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.navigation.TomTomNavigation
import com.tomtom.sdk.navigation.ui.NavigationFragment
import com.tomtom.sdk.routing.RoutePlanner
import com.tomtom.sdk.routing.options.RoutePlanningOptions
import com.tomtom.sdk.routing.route.Route
import androidx.fragment.app.commit
import com.tomtom.sdk.maps.display.MapFragment




class MainActivity : AppCompatActivity() {

    private lateinit var mapFragment: MapFragment

    private lateinit var tomTomMap: TomTomMap

    private lateinit var navigationTileStore: NavigationTileStore

    private lateinit var locationProvider: LocationProvider

    private lateinit var onLocationUpdateListener: OnLocationUpdateListener

    private lateinit var routePlanner: RoutePlanner

    private var route: Route? = null

    private lateinit var routePlanningOptions: RoutePlanningOptions

    private lateinit var tomTomNavigation: TomTomNavigation

    private lateinit var navigationFragment: NavigationFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


//        initMap()

//        initNavigationTileStore()

//        initLocationProvider()

//        initRouting()

//        initNavigation()

    }
}

@Composable
fun TomTomMap() {
    val fragmentManager = LocalContext.current as? AppCompatActivity
        ?: throw IllegalStateException("Activity must be AppCompatActivity")

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            FragmentContainerView(context).apply {
                id = View.generateViewId()

                // Attach MapFragment to this container
                fragmentManager.supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add(id, MapFragment::class.java, null)
                }
            }
        }
    )
}

@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TomTom Map") })
        }
    ) {
        TomTomMap()
    }
}


