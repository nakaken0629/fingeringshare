package red.itvirtuoso.ourfingering.ui

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import red.itvirtuoso.ourfingering.R

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.name
        private const val REQUEST_CAMERA = 1001
    }
    private var fabHelper : FabHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fabHelper = FabHelper(applicationContext, fab, fab_background, listOf(fab_camera_layout, fab_library_layout))

        fab.setOnClickListener { fabHelper?.toggle() }
        fab_camera.setOnClickListener{ fabCameraClick() }
    }

    private fun fabCameraClick() {
        fabHelper?.close()
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
