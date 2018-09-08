package red.itvirtuoso.ourfingering.ui

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.leinardi.android.speeddial.SpeedDialActionItem
import kotlinx.android.synthetic.main.activity_main.*
import red.itvirtuoso.ourfingering.R

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.name
        private const val REQUEST_CAMERA = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initSpeedDial(savedInstanceState)
    }

    private fun initSpeedDial(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.fab_camera, android.R.drawable.ic_menu_camera).create())
            speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.fab_gallery, android.R.drawable.ic_menu_gallery).create())
        }
    }

    private fun fabCameraClick() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CAMERA -> {
                onCamera(resultCode, data)
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun onCamera(resultCode: Int, data: Intent?) {
    }
}
