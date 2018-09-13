package red.itvirtuoso.ourfingering.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import com.leinardi.android.speeddial.SpeedDialActionItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import red.itvirtuoso.ourfingering.R
import java.io.File
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.name
        private const val REQUEST_CAMERA = 1001
        private const val REQUEST_CAMERA_PERMISSION = 1002
        private const val REQUEST_GALLERY = 1003
        private const val REQUEST_POST = 1004
    }

    private var mCurrentPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initSpeedDial(savedInstanceState)
        speedDial.setOnActionSelectedListener {
            speedDial.close()
            when (it.id) {
                R.id.fab_camera -> {
                    fabCameraClick()
                }
                R.id.fab_gallery -> {
                    fabGalleryClick()
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun initSpeedDial(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            listOf(
                    Pair(R.id.fab_camera, android.R.drawable.ic_menu_camera),
                    Pair(R.id.fab_gallery, android.R.drawable.ic_menu_gallery)
            ).forEach {
                speedDial.addActionItem(SpeedDialActionItem.Builder(it.first, it.second).create())
            }
        }
    }

    private fun fabCameraClick(): Boolean {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(packageManager)?.let {
            if (checkCameraPermission()) {
                takePicture()
            } else {
                grantCameraPermission()
            }
        } ?: run {
            Snackbar.make(main_layout, R.string.main_no_camera, Snackbar.LENGTH_LONG).show()
        }
        return true
    }

    private fun checkCameraPermission() = PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)

    private fun createImageFile(): File? {
        try {// Create an image file name
            val imageFileName = "JPEG_" + UUID.randomUUID().toString()
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image = File.createTempFile(
                    imageFileName, /* prefix */
                    ".jpg", /* suffix */
                    storageDir      /* directory */
            )

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = image.getAbsolutePath()
            return image
        } catch (e: IOException) {
            mCurrentPhotoPath = null
            return null
        }
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager).let {
            val photoFile = createImageFile() ?: run {
                Snackbar.make(main_layout, R.string.global_cannot_create_temp_file, Snackbar.LENGTH_LONG).show()
                return
            }
            val photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private fun grantCameraPermission() =
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION)

    private fun fabGalleryClick(): Boolean {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.type = "image/*";
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
        if (resultCode != Activity.RESULT_OK) return
        val photoPath = mCurrentPhotoPath ?: return

        val intent = PostActivity.createIntent(applicationContext, photoPath)
        startActivityForResult(intent, REQUEST_POST)
    }

    private fun onGallery(resultCode: Int, data: Intent?) {

    }
}
