package com.example.travelbox.presentation.view.home

import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Color
import android.graphics.Rect
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
        mBanner.setCurrentItem(1, false)   // 애니메이션 설정x


        // 페이지 한 개씩 미리 로드
        mBanner.offscreenPageLimit = 3


        // 배너가 양옆으로 보이도록 설정
        mBanner.clipToPadding = false
        mBanner.clipChildren = false



        // 페이지 전환 애니메이션 및 캐러셀 효과
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
        mBanner.setPadding(offsetPx, 0, offsetPx, 0)


        // 배너 간격 조정
        addBannerSpacing(pageMarginPx)

        // 배너 전환 애니메이션
        setBannerAnimation()



        // 인기 게시물 클릭 시

        binding.tvBestPost.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, BestPostFragment())
                .addToBackStack(null)
                .commit()
        }


        // 뒤로 가기 버튼
        binding.ivBackButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, BestPostFragment())  // BestPostFragment로 전환
                .addToBackStack(null)
                .commit()
        }




        return binding.root

    }

    // 배너 간격 조정
    private fun addBannerSpacing(pageMarginPx: Int) {
        val recyclerView = mBanner.getChildAt(0) as RecyclerView
        recyclerView.apply {
            val itemDecoration = object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
                ) {
                    outRect.left = pageMarginPx / 2
                    outRect.right = pageMarginPx / 2
                }
            }
            addItemDecoration(itemDecoration)
        }
    }

    // 배너 전환 애니메이션

    private fun setBannerAnimation() {
        mBanner.setPageTransformer { page, position ->
            val scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f
            page.scaleY = scaleFactor
            page.alpha = scaleFactor
        }
    }


    // 폰트 배치, 스타일 설정
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