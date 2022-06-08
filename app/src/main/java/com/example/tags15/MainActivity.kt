package com.example.tags15


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

import kotlinx.android.synthetic.main.main_activity.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private val digitList = mutableListOf<String>()
    var stepCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        for (i in 0 until 16) {
            digitList.add(i.toString())
        }

        initTagView(digitList.shuffled() as MutableList<String>)

        btn_reset_game.setOnClickListener {
            resetGame()
        }


    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initTagView(tagsList: MutableList<String>) {

        for (tag in tagsList) {
            val ib = AppCompatButton(this)
            val emptyButton = AppCompatButton(this)
            createTag(tag, ib)
            createEmptyButton(emptyButton)

            ib.setOnClickListener {
                try {
                    moveTag(ib, emptyButton)

                } catch (e: Exception) {
                    Log.v("TAGZZ", "Дошли до конца списка")
                }
            }
            tags_layout.addView(ib)

        }
    }

    private fun createEmptyButton(emptyButton: AppCompatButton) {
        val params2 = ViewGroup.MarginLayoutParams(
            10,
            10
        )
        emptyButton.id = 0
        emptyButton.layoutParams = params2
        emptyButton.setBackgroundColor(Color.TRANSPARENT)
        emptyButton.text = ""

    }

    private fun createTag(tag: String, ib: Button) {
        val params = ViewGroup.MarginLayoutParams(
            ( this.getDisplayMetrics() / 4 - convertDpPixelsTo(13)).roundToInt(),
            (this.getDisplayMetrics() / 4 - convertDpPixelsTo(13)).roundToInt()
        )

        params.setMargins(2, 2, 2, 2)

        val typeface = resources.getFont(R.font.oswald_regular)
        //or to support all versions use

        ib.id = tag.toInt()
        ib.textSize = 40F
        ib.typeface = typeface
        ib.includeFontPadding = false
        ib.setTextColor(Color.parseColor("#D3D3D3"))
        ib.layoutParams = params
        if (tag.toInt() != 0) {
            ib.background = this.getDrawable(R.drawable.bg_button)
            ib.text = tag
        } else {
            ib.setBackgroundColor(Color.TRANSPARENT)
            ib.text = ""
        }

    }

    private fun moveTag(it: Button, emptyButton: Button) {
        val x = tags_layout.indexOfChild(it) / tags_layout.columnCount
        val y = tags_layout.indexOfChild(it) % tags_layout.columnCount

        val itemPosition = tags_layout.indexOfChild(it)


        val checkNeighborInfo = if (y != 3) {
            tags_layout.getChildAt(itemPosition + 1).id
        } else {
            404
        }

        val checkNeighborLeftInfo = if (y != 0) {
            tags_layout.getChildAt(itemPosition - 1).id
        } else {
            404
        }

        val checkNeighborUpInfo = if (x != 0) {
            tags_layout.getChildAt(itemPosition - 4).id
        } else {
            404
        }

        val checkNeighborDownInfo = if (x != 3) {
            tags_layout.getChildAt(itemPosition + 4).id
        } else {
            404
        }

        if (y != 3 && checkNeighborInfo == 0) {
            stepCount++
            tv_step_count.text = "Ходов: $stepCount"
//                        AdditiveAnimator.animate(tags_layout.getChildAt(itemPosition + 1))
//                            .translationX(-tags_layout.getChildAt(itemPosition + 1).measuredWidth.toFloat())
//                            .start()
//                        AdditiveAnimator.animate(tags_layout.getChildAt(itemPosition + 1))
//                            .translationX(-tags_layout.getChildAt(itemPosition + 1).measuredWidth.toFloat())
//                            .start()

            tags_layout.removeView(tags_layout.getChildAt(itemPosition))
            tags_layout.removeView(tags_layout.getChildAt(itemPosition))
            tags_layout.addView(emptyButton, itemPosition)
            tags_layout.addView(it, itemPosition + 1)

        } else if (y != 0 && checkNeighborLeftInfo == 0) {
            stepCount++
            tv_step_count.text = "Ходов: $stepCount"
//                        AdditiveAnimator.animate(tags_layout.getChildAt(itemPosition))
//                            .translationX(-tags_layout.getChildAt(itemPosition).measuredWidth.toFloat())
//                            .start()
//                        AdditiveAnimator.animate(tags_layout.getChildAt(itemPosition - 1))
//                            .translationX(-tags_layout.getChildAt(itemPosition - 1).measuredWidth.toFloat())
//                            .start()

            tags_layout.removeView(tags_layout.getChildAt(itemPosition))
            tags_layout.removeView(tags_layout.getChildAt(itemPosition - 1))


            tags_layout.addView(it, itemPosition - 1)
            tags_layout.addView(emptyButton, itemPosition)


        } else if (x != 0 && checkNeighborUpInfo == 0) {
            stepCount++
            tv_step_count.text = "Ходов: $stepCount"
//                        AdditiveAnimator.animate(tags_layout.getChildAt(itemPosition))
//                            .translationX(-tags_layout.getChildAt(itemPosition).measuredWidth.toFloat())
//                            .start()
//                        AdditiveAnimator.animate(tags_layout.getChildAt(itemPosition - 1))
//                            .translationX(-tags_layout.getChildAt(itemPosition - 1).measuredWidth.toFloat())
//                            .start()

            tags_layout.removeView(tags_layout.getChildAt(itemPosition))
            tags_layout.removeView(tags_layout.getChildAt(itemPosition - 4))

            tags_layout.addView(it, itemPosition - 4)
            tags_layout.addView(emptyButton, itemPosition)


        } else if (x != 3 && checkNeighborDownInfo == 0) {
            stepCount++
            tv_step_count.text = "Ходов: $stepCount"
//                        AdditiveAnimator.animate(tags_layout.getChildAt(itemPosition))
//                            .translationX(-tags_layout.getChildAt(itemPosition).measuredWidth.toFloat())
//                            .start()
//                        AdditiveAnimator.animate(tags_layout.getChildAt(itemPosition - 1))
//                            .translationX(-tags_layout.getChildAt(itemPosition - 1).measuredWidth.toFloat())
//                            .start()


            tags_layout.removeView(tags_layout.getChildAt(itemPosition + 4))
            tags_layout.removeView(tags_layout.getChildAt(itemPosition))

            tags_layout.addView(emptyButton, itemPosition)
            tags_layout.addView(it, itemPosition + 4)
        }
    }

    fun resetGame(){
        tags_layout.removeAllViews()
        digitList.clear()
        for (i in 0 until 16) {
            digitList.add(i.toString())
        }

        initTagView(digitList.shuffled() as MutableList<String>)
        stepCount = 0
        tv_step_count.text = "Ходов: $stepCount"

    }


    fun Context.convertDpToPix(dp: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    ).toInt()

    fun Context.convertDpPixelsTo(dp: Int): Float {
        val displayMetrics = resources.displayMetrics
        Log.v("15xxx","${Resources.getSystem().displayMetrics.density} ${DisplayMetrics.DENSITY_DEFAULT} ${ DisplayMetrics.DENSITY_280}" )

        return (dp * Resources.getSystem().displayMetrics.density)
    }

    fun Context.getDisplayMetrics(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return Resources.getSystem().displayMetrics.widthPixels
    }




}

