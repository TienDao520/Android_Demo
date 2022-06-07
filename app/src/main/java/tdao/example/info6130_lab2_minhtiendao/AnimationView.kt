package tdao.example.info6130_lab2_minhtiendao

import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * TODO: document your custom view class.
 */
class AnimationView : View {

    private var _exampleString: String? = null // TODO: use a default from R.string...
    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...

    private lateinit var textPaint: TextPaint
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f

    //add new variables
    private val ANIMATION_DURATION = 4000
    private val ANIMATION_DELAY: Long = 1000
    private val COLOR_ADJUSTER =5
    private var mX = 0f
    private var mY = 0f
    private var mRadius = 0f
    private val mPaint = Paint()
    private val mAnimatorSet = AnimatorSet()

    private val rectf: RectF = RectF(0F, 0F, 400F, 200F)
    private val footingpaint = Paint ()
    /**
     * The text to draw
     */
    var exampleString: String?
        get() = _exampleString
        set(value) {
            _exampleString = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * The font color
     */
    var exampleColor: Int
        get() = _exampleColor
        set(value) {
            _exampleColor = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this dimension is the font size.
     */
    var exampleDimension: Float
        get() = _exampleDimension
        set(value) {
            _exampleDimension = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
//        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
//        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.AnimationView, defStyle, 0
        )

        _exampleString = a.getString(
            R.styleable.AnimationView_exampleString
        )
        _exampleColor = a.getColor(
            R.styleable.AnimationView_exampleColor,
            exampleColor
        )
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _exampleDimension = a.getDimension(
            R.styleable.AnimationView_exampleDimension,
            exampleDimension
        )

        if (a.hasValue(R.styleable.AnimationView_exampleDrawable)) {
            exampleDrawable = a.getDrawable(
                R.styleable.AnimationView_exampleDrawable
            )
            exampleDrawable?.callback = this
        }

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
        }

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint.let {
            it.textSize = exampleDimension
            it.color = exampleColor
            textWidth = it.measureText(exampleString)
            textHeight = it.fontMetrics.bottom
        }
    }

    //Modify the canvas draw
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        footingpaint.setARGB(255, 128, 128, 128)
        canvas.drawRect(rectf, footingpaint)
    }
}