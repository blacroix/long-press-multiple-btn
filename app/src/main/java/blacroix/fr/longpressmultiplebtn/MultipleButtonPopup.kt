package blacroix.fr.longpressmultiplebtn

import android.annotation.SuppressLint
import android.os.Handler
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.PopupWindow

@SuppressLint("InflateParams")
class MultipleButtonPopup(private val view: View, private val parentView: View) {

    companion object {
        const val DELAY_BEFORE_WIGGLE = 200L
    }

    private var popup: PopupWindow? = null
    private val handler = Handler()
    private val wiggle: () -> Unit = {
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.view_wiggle)
        view.startAnimation(animation)
    }
    private val layoutInflater by lazy {
        LayoutInflater.from(view.context)
    }

    init {
        view.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                handler.postDelayed(wiggle, DELAY_BEFORE_WIGGLE)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacksAndMessages(null)
                if (popup != null) {
                    popup?.dismiss()
                    popup = null
                }
            }
            false
        }
        view.setOnLongClickListener {
            val layout = layoutInflater.inflate(R.layout.popup_multiple_btn, null)
            popup = PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            popup?.animationStyle = R.style.PopupOneBetClickAnim
            popup?.showAtLocation(
                    parentView,
                    Gravity.TOP or Gravity.START,
                    view.x.toInt() - view.width,
                    view.y.toInt() + 25)
            true
        }
    }
}