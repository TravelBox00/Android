package com.example.travelbox.presentation.view.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var mBanner: ViewPager2




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)


        setTextStyle()




        mBanner = binding.bannerList
        bannerAdapter = BannerAdapter(this, 3)
        mBanner.adapter = bannerAdapter

        //수평 슬라이드 설정
        mBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 배너 시작지점 설정
        mBanner.setCurrentItem(1)


        // 페이지 한 개씩 미리 로드
        mBanner.offscreenPageLimit = 3




        // 페이지 전환 애니메이션 및 캐러셀 효과
        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset)

        mBanner.setPageTransformer { page, position ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            if (position < -1) {
                page.setTranslationX(-myOffset)
            } else if (position <= 1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.setTranslationX(myOffset)
                page.setScaleY(scaleFactor)
                page.setAlpha(scaleFactor)
            } else {
                page.setAlpha(0f)
                page.setTranslationX(myOffset)
            }


        }

        // 배너 양 옆에 배너가 보이도록 패딩 설정
        mBanner.setPadding(pageMargin, 0, pageMargin, 0)
        mBanner.clipToPadding = false


        mBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // 페이지가 스크롤 될 때 이벤트 처리
                if (positionOffsetPixels == 0) {
                    mBanner.setCurrentItem(position)
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })








        return binding.root

    }


    private fun setTextStyle() {
        val content = binding.tvTravelRecord.text.toString()
        val spannableString = SpannableString(content)

        val start = content.indexOf("여행 기록")
        val end = start + "여행 기록".length

        if(start >= 0) {
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#007151")),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableString.setSpan(
                RelativeSizeSpan(1.3f),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }


        binding.tvTravelRecord.text = spannableString
    }


}