package red.itvirtuoso.ourfingering.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_post.*
import red.itvirtuoso.ourfingering.PhotoUtils
import red.itvirtuoso.ourfingering.R

class PostActivity : AppCompatActivity() {
    companion object {
        private val TAG = PostActivity::class.java.name
        private const val KEY_PHOTO_PATH = "PHOTO_PATH"
        private const val BUNDLE_PHOTO_PATH = "PHOTO_PATH"
        private const val BUNDLE_INSTRUMENT = "INSTRUMENT"
        private const val BUNDLE_COMPOSER = "COMPOSER"
        private const val BUNDLE_TITLE = "TITLE"

        fun createIntent(context: Context, photoPath: String): Intent {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra(KEY_PHOTO_PATH, photoPath)
            return intent
        }
    }

    private lateinit var photoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.inflateMenu(R.menu.menu_post)

        savedInstanceState?.run {
            photoPath = getString(BUNDLE_PHOTO_PATH)
            instrumentEdit.setText(getString(BUNDLE_INSTRUMENT))
            composerEdit.setText(getString(BUNDLE_COMPOSER))
            titleEdit.setText(getString(BUNDLE_TITLE))
        } ?: run {
            photoPath = intent.getStringExtra(KEY_PHOTO_PATH)
        }
        fingeringImage.viewTreeObserver.addOnGlobalLayoutListener { drawFingering() }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.run {
            putString(BUNDLE_PHOTO_PATH, photoPath)
            putString(BUNDLE_INSTRUMENT, instrumentEdit.text.toString())
            putString(BUNDLE_COMPOSER, composerEdit.text.toString())
            putString(BUNDLE_TITLE, titleEdit.text.toString())
        }
    }

    private fun drawFingering() {
        fingeringImage.setImageBitmap(PhotoUtils.createBitmap(fingeringImage.width, fingeringImage.height, photoPath))
    }
}
