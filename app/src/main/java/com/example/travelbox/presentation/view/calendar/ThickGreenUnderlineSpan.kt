import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.LineBackgroundSpan

class ThickGreenUnderlineSpan : LineBackgroundSpan {
    override fun drawBackground(
        c: Canvas, p: Paint,
        left: Int, right: Int, top: Int, baseline: Int, bottom: Int,
        text: CharSequence, start: Int, end: Int, lineNumber: Int
    ) {
        val paint = Paint()
        paint.color = Color.parseColor("#00A879") // ✅ 초록색
        paint.style = Paint.Style.FILL_AND_STROKE // ✅ 선과 채우기 둘 다 적용
        paint.strokeWidth = 16f // ✅ 조금 더 두껍게

        // ✅ 밑줄을 더 아래로 위치 조정
        val rect = RectF(left.toFloat(), baseline.toFloat() + 12f, right.toFloat(), baseline.toFloat() + 20f)
        c.drawRect(rect, paint)
    }
}
