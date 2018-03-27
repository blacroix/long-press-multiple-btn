package blacroix.fr.longpressmultiplebtn

import android.annotation.SuppressLint
import android.os.Handler
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow

@SuppressLint("InflateParams")
class MultipleButtonPopup(
        private val view: View,
        private val parentView: View,
        private val input: List<Double>,
        private val onClick: (Double) -> Unit) {

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
    private val popupLayout by lazy {
        layoutInflater.inflate(R.layout.popup_multiple_button, null)
    }

    init {
        view.setOnTouchListener { _, event ->
            wiggle(event)
            onTouchUp(event)
            false
        }
        view.setOnLongClickListener {
            showPopupWindow()
            true
        }
    }

    private fun onTouchUp(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_UP) {
            handler.removeCallbacksAndMessages(null)
            if (popup != null) {
                handleClick(event.rawX, event.rawY)
                popup?.dismiss()
                popup = null
            }
        }
    }

    private fun wiggle(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            handler.postDelayed(wiggle, DELAY_BEFORE_WIGGLE)
        }
    }

    private fun showPopupWindow() {
        val multipleBtnGroup = popupLayout.findViewById<LinearLayout>(R.id.multipleBtnGroup)
        input.forEach {
            val btn = layoutInflater.inflate(R.layout.view_multiple_popup_button, multipleBtnGroup, false) as Button
            btn.text = "$it"
            multipleBtnGroup.addView(btn)
        }
        popup = PopupWindow(popupLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popup?.animationStyle = R.style.PopupOneBetClickAnim
        popup?.showAtLocation(
                parentView,
                Gravity.TOP or Gravity.START,
                view.x.toInt() - view.width,
                view.y.toInt() + 25)
    }

    private fun handleClick(x: Float, y: Float) {
        popup?.run {
            val group = contentView.findViewById<LinearLayout>(R.id.multipleBtnGroup)
            val position: Int? = (0 until group.childCount).find {
                val button = group.getChildAt(it)
                val location = IntArray(2)
                button.getLocationOnScreen(location)
                x >= location[0] && x <= location[0] + button.width && y >= location[1] && y <= location[1] + button.height
            }
            position?.run {
                onClick.invoke((group.getChildAt(position) as Button).text.toString().toDouble())
            }
        }
    }
}