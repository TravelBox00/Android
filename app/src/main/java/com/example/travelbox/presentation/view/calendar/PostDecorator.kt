//package com.example.travelbox.presentation.view.calendar
//
//import android.content.Context
//import android.graphics.*
//import android.graphics.drawable.Drawable
//import android.util.Log
//import androidx.core.content.ContextCompat
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.target.CustomTarget
//import com.bumptech.glide.request.transition.Transition
//import com.example.travelbox.R
//import com.example.travelbox.data.repository.calendar.PostData
//import com.prolificinteractive.materialcalendarview.CalendarDay
//import com.prolificinteractive.materialcalendarview.DayViewDecorator
//import com.prolificinteractive.materialcalendarview.DayViewFacade
//
//class PostDecorator(
//    private val context: Context,
//    private val posts: List<PostData>
//) : DayViewDecorator {
//
//    private val postImageCache = mutableMapOf<CalendarDay, Bitmap?>()
//
//    init {
//        loadPostImages()
//    }
//
//    override fun shouldDecorate(day: CalendarDay): Boolean {
//        return postImageCache.containsKey(day)
//    }
//
//    override fun decorate(view: DayViewFacade) {
//        val date = view.date // ❌ 이 부분을 직접 사용하면 안됨 → CalendarDay 받아서 사용해야 함
//        val imageBitmap = postImageCache[date]
//
//        if (imageBitmap != null) {
//            view.addSpan { canvas, text, x, y, paint ->
//                val radius = paint.textSize * 1.5f // ✅ 원 크기 조정
//
//                // ✅ 흰색 원 그리기
//                val circlePaint = Paint().apply {
//                    color = Color.WHITE
//                    style = Paint.Style.FILL
//                    isAntiAlias = true
//                }
//                canvas.drawCircle(x, y - radius, radius, circlePaint)
//
//                // ✅ 이미지 크기 설정
//                val imageSize = (radius * 1.8).toInt()
//                val imageLeft = (x - imageSize / 2).toInt()
//                val imageTop = (y - radius - imageSize / 2).toInt()
//
//                // ✅ 원 안에 대표 이미지 그리기
//                canvas.drawBitmap(
//                    imageBitmap,
//                    null,
//                    Rect(imageLeft, imageTop, imageLeft + imageSize, imageTop + imageSize),
//                    null
//                )
//            }
//        }
//    }
//
//    private fun loadPostImages() {
//        posts.groupBy { post ->
//            val dateParts = post.postDate.substring(0, 10).split("-")
//            CalendarDay.from(dateParts[0].toInt(), dateParts[1].toInt(), dateParts[2].toInt())
//        }.forEach { (date, postList) ->
//            val firstImageUrl = postList.firstOrNull { it.imageURL != null }?.imageURL
//
//            if (firstImageUrl != null) {
//                Glide.with(context)
//                    .asBitmap()
//                    .load(firstImageUrl)
//                    .circleCrop()  // ✅ 원형으로 변환
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            postImageCache[date] = resource
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                            postImageCache[date] = null
//                        }
//                    })
//            } else {
//                postImageCache[date] = null
//            }
//        }
//    }
//}
