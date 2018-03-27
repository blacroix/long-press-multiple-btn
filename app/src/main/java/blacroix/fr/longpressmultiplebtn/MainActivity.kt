package blacroix.fr.longpressmultiplebtn

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var popup: PopupWindow? = null
        selection.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (popup != null) {
                    popup?.dismiss()
                    popup = null
                }
            }
            false
        }
        selection.setOnLongClickListener {
            val layout = layoutInflater.inflate(R.layout.popup_multiple_btn, null)
            popup = PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            popup?.animationStyle = R.style.PopupOneBetClickAnim
            popup?.showAtLocation(
                    baseLayout,
                    Gravity.TOP or Gravity.START,
                    selection.x.toInt() - selection.width,
                    selection.y.toInt() + 25)
            true
        }
    }
}
