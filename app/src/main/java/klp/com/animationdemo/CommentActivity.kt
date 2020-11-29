package klp.com.animationdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CommentActivity: AppCompatActivity() {

    companion object {
        fun startActivity(fromActivity: AppCompatActivity) {
            val intent = Intent(fromActivity, CommentActivity::class.java)
            fromActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
    }
}