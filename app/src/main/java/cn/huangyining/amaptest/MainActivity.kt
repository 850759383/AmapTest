package cn.huangyining.amaptest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Poi
import com.amap.api.navi.AmapNaviPage
import com.amap.api.navi.AmapNaviParams
import com.amap.api.navi.AmapNaviType
import com.amap.api.navi.AmapPageType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        findViewById<Button>(R.id.naviBtn).setOnClickListener {
            val endPosition = Poi("重点", LatLng(30.010851, 121.150207), null)
            val params =
                AmapNaviParams(null, null, endPosition, AmapNaviType.DRIVER, AmapPageType.ROUTE)
            AmapNaviPage.getInstance()
                .showRouteActivity(this,
                    params,
                    null)
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(PERMISSIONS)
        }
    }

    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (!granted) {
                Toast.makeText(this, "未获得权限", Toast.LENGTH_SHORT).show()
            }
        }
}