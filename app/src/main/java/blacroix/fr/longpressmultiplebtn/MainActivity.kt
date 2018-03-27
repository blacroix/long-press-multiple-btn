package blacroix.fr.longpressmultiplebtn

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val input = listOf(1.0, 2.0, 3.0)
        val onClick = { output: Double ->
            Toast.makeText(this, "U click on $output", Toast.LENGTH_SHORT).show()
        }
        MultipleButtonPopup(selection, baseLayout, input, onClick)
    }
}
