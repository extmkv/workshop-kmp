package xyz.mlumeau.kosmos

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.mlumeau.kosmos.kore.APOD
import xyz.mlumeau.kosmos.kore.createApplicationScreenMessage
import xyz.mlumeau.kosmos.viewmodel.ApodViewModel

class MainActivity : AppCompatActivity() {

    private val apodViewModel: ApodViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title_tv.text = createApplicationScreenMessage()

        apodViewModel.apod.observe(this, Observer { apod -> updateAPODData(apod) })
    }

    private fun updateAPODData(apod: APOD) {
        if (apod.media_type == "image" && !apod.url.isNullOrEmpty()) {
            Picasso.get().load(apod.url).fit().centerCrop().into(apod_iv)
            title_tv.text = apod.title
            desc_tv.text = apod.explanation
        } else {
            title_tv.text = "Sorry, no picture today :("
        }
        progress.visibility = View.GONE
    }
}
