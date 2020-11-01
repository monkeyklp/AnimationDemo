package klp.com.animationdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
//        val moveView = findViewById<MoveBall3>(R.id.moveBall)
//        moveView.setCallback { x, y -> moveView.scrollTo(x, y) }
    }
}