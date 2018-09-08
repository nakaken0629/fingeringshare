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
        private const val REQUEST_GALLERY = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initSpeedDial(savedInstanceState)
        speedDial.setOnActionSelectedListener {
            speedDial.close()
            when (it.id) {
                R.id.fab_camera -> { fabCameraClick() }
                R.id.fab_gallery -> { fabGalleryClick() }
                else -> { false }
            }
        }
    }

    private fun initSpeedDial(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.fab_camera, android.R.drawable.ic_menu_camera).create())
            speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.fab_gallery, android.R.drawable.ic_menu_gallery).create())
        }
    }

    private fun fabCameraClick() : Boolean {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_CAMERA)
        }
        return true
    }

    private fun fabGalleryClick(): Boolean {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_GALLERY)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CAMERA -> {
                onCamera(resultCode, data)
            }
            REQUEST_GALLERY -> {
                onGallery(resultCode, data)
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun onCamera(resultCode: Int, data: Intent?) {

    }

    private fun onGallery(resultCode: Int, data: Intent?) {

    }
}
