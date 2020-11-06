package klp.com.animationdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import klp.com.animationdemo.view.MoveBall3

class BesselActivity : AppCompatActivity() {

    companion object {
        fun startActivity(fromActivity: AppCompatActivity) {
            val intent = Intent(fromActivity, BesselActivity::class.java)
            fromActivity.startActivity(intent)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bessel)
        SVGAParser(this).parse("Goddess.svga", object: SVGAParser.ParseCompletion{
            override fun onComplete(videoItem: SVGAVideoEntity) {
                findViewById<SVGAImageView>(R.id.svg).setImageDrawable(SVGADrawable(videoItem))
                findViewById<SVGAImageView>(R.id.svg).startAnimation()
            }

            override fun onError() {
            }

        })
        findViewById<Button>(R.id.btn).setOnClickListener {
            findViewById<SVGAImageView>(R.id.svg).stopAnimation()
           findViewById<SVGAImageView>(R.id.svg).visibility = View.GONE
        }

        findViewById<MoveBall3>(R.id.moveBall).setOnClickListener {
            Toast.makeText(it.context, "show", Toast.LENGTH_SHORT).show()
        }
//        val moveView = findViewById<MoveBall3>(R.id.moveBall)
//        moveView.setCallback { x, y -> moveView.scrollTo(x, y) }
    }
}