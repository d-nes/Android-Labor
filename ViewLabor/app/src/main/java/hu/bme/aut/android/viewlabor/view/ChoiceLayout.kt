package hu.bme.aut.android.viewlabor.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import hu.bme.aut.android.viewlabor.R

class ChoiceLayout : LinearLayout {

    private var multiple: Int = 1

    companion object {
        private const val DIVIDER_NONE = 0
        private const val DIVIDER_SIMPLE = 1
        private const val DIVIDER_DOUBLE = 2
    }

    private var dividerType: Int = DIVIDER_NONE

    constructor(context: Context) : super(context, null) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = VERTICAL
        
        if (attrs == null) {
            return
        }

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ChoiceLayout)
        try {
            multiple = attributes.getInt(R.styleable.ChoiceLayout_multiple, 1)
            dividerType = attributes.getInt(R.styleable.ChoiceLayout_dividerType, DIVIDER_NONE)
        } finally {
            attributes.recycle()
        }

    }

    override fun addView(child: View) {
        if (childCount > 0) {
            addDivider()
        }
        super.addView(child)
        refreshAfterAdd(child)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams?) {
        if (childCount > 0) {
            addDivider()
        }
        super.addView(child, params)
        refreshAfterAdd(child)
    }

    private fun getSelectedCount(): Int {
        var selectedCount = 0
        for (i in 0 until childCount) {
            if (getChildAt(i).isSelected) {
                selectedCount++
            }
        }
        return selectedCount
    }

    private fun refreshAfterAdd(child: View) {
        child.isClickable = true
        child.setOnClickListener { view ->
            if(getSelectedCount() == 3){
                val v = getChildAt(0) //TODO figure out
                v.isSelected = false
            }
            if (multiple > 1) {
                if (view.isSelected || getSelectedCount() < multiple) {
                    view.isSelected = !view.isSelected
                }
            } else {
                for (i in 0 until childCount) {
                    val v = getChildAt(i)
                    v.isSelected = (v == view)
                }
            }
        }
    }

    private fun addDivider() {
        if (dividerType != DIVIDER_NONE) {
            val divider = ImageView(context)
            val imageResource = when (dividerType) {
                DIVIDER_SIMPLE -> R.drawable.choice_divider_simple
                DIVIDER_DOUBLE -> R.drawable.choice_divider_double
                else -> throw IllegalArgumentException("No such divider type!")
            }
            divider.setImageResource(imageResource)
            val layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            super.addView(divider, layoutParams)
        }
    }
}