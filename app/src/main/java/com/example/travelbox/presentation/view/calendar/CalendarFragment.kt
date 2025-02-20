package com.example.travelbox.presentation.view.calendar

import ThickGreenUnderlineSpan
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.travelbox.R
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.calendar.CalendarQueryEvent
import com.example.travelbox.data.repository.calendar.CalendarRepository
import com.example.travelbox.data.repository.calendar.PostRepository
import com.example.travelbox.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.DayOfWeek
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private var selectedDate: CalendarDay = CalendarDay.today()  // ✅ 기본값: 오늘 날짜
    private var userTag: String? = null // ✅ 로그인한 유저의 userTag
    private var lastFetchedEvents: List<CalendarQueryEvent> = emptyList()
    private var selectedMonth: Int = CalendarDay.today().month - 1 // ✅ 현재 월 (0-based index)
    private var lastFetchedPosts: List<PostItem> = emptyList() // ✅ 게시물 데이터를 저장할 리스트

    private val months = listOf(
        "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
        "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
    )

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        AndroidThreeTen.init(requireContext())
        userTag = ApiNetwork.getUserTag()
        // ✅ 요일 변경: '일' → 'S', '월' → 'M' 등으로 변경
        val customWeekFormatter = WeekDayFormatter { dayOfWeek ->
            when (dayOfWeek) {
                DayOfWeek.SUNDAY -> "S"
                DayOfWeek.MONDAY -> "M"
                DayOfWeek.TUESDAY -> "T"
                DayOfWeek.WEDNESDAY -> "W"
                DayOfWeek.THURSDAY -> "T"
                DayOfWeek.FRIDAY -> "F"
                DayOfWeek.SATURDAY -> "S"
                else -> "?"
            }
        }

        binding.calendarView.setWeekDayFormatter(customWeekFormatter)
        // ✅ 현재 날짜에 맞게 연도와 월 초기화
        val today = CalendarDay.today()
        updateYearAndMonth(today.year, today.month)
        // ✅ 캘린더 설정
        setupCalendar()

        // ✅ 헤더 숨기기 적용
        binding.calendarView.setTitleFormatter { "" }
        binding.calendarView.topbarVisible = false



        // ✅ 캘린더에서 월이 변경될 때 연도와 월 업데이트
        binding.calendarView.setOnMonthChangedListener(OnMonthChangedListener { _, date ->
            updateYearAndMonth(date.year, date.month)
        })

        // ✅ 기존 기능 유지: 월 탭 클릭하면 해당 월로 이동
        binding.monthTabs.children.forEachIndexed { index, view ->
            (view as TextView).setOnClickListener {
                updateMonthTabs(index)
                scrollToMonth(index)
                binding.calendarView.currentDate = CalendarDay.from(today.year, index + 1, 1)
            }

        }

//        // ✅ 날짜 선택 이벤트
//        binding.calendarView.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
//            selectedDate = date  // ✅ 선택한 날짜 저장
//            binding.calendarView.invalidateDecorators()
//            Toast.makeText(requireContext(), "선택한 날짜: ${date.year}.${date.month}.${date.day}", Toast.LENGTH_SHORT).show()
//        })

        // ✅ Fab 버튼 클릭 시 스케줄 이동 설정
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fab_menu_container, FabMenuFragment { openScheduleActivity() })  // ✅ Fab 메뉴에서 스케줄 버튼 클릭 시 실행
                .commit()
        }

        // ✅ 캘린더에서 월이 변경될 때 연도와 월 업데이트
        binding.calendarView.setOnMonthChangedListener(OnMonthChangedListener { _, date ->
            updateYearAndMonth(date.year, date.month) // ✅ 월 변경 시 UI 갱신
            if (userTag != null) {
                fetchUserCalendarEvents(date.year, date.month) // ✅ 일정 다시 불러오기
            }
        })
        return binding.root
    }
    private fun setupCalendar() {

        // ✅ 오늘 날짜 데코레이터 추가
        binding.calendarView.addDecorator(TodayDecorator(requireContext()))

        // ✅ 날짜 선택 이벤트 리스너

// ✅ 날짜 선택 이벤트 리스너 (밑줄은 유지)
        binding.calendarView.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
            selectedDate = date  // ✅ 선택한 날짜 저장

            // ✅ 기존 데코레이터를 삭제하고 다시 추가 (PostDecorator 유지)
            binding.calendarView.removeDecorators()
            binding.calendarView.addDecorator(TodayDecorator(requireContext())) // 오늘 날짜 유지
            binding.calendarView.addDecorator(CalendarEventsDecorator(lastFetchedEvents)) // 일정 밑줄 유지
            binding.calendarView.addDecorator(SelectedDateDecorator(requireContext(), selectedDate)) // 선택한 날짜 적용
            binding.calendarView.addDecorator(PostDecorator(requireContext(), lastFetchedPosts.mapNotNull { post ->
                val parts = post.postDate.split("-")
                if (parts.size == 3) {
                    CalendarDay.from(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                } else {
                    null  // ✅ 날짜 형식이 잘못된 경우 제외
                }
            })) // ✅ PostDecorator 다시 추가

            // ✅ 즉시 반영
            binding.calendarView.invalidateDecorators()

            val selectedDateStr = "%04d-%02d-%02d".format(date.year, date.month, date.day)

//            Toast.makeText(requireContext(), "선택한 날짜: ${date.year}.${date.month}.${date.day}", Toast.LENGTH_SHORT).show()
            val eventsForDate = lastFetchedEvents.filter { event ->
                val startDate = event.travelStartDate.substring(0, 10) // "YYYY-MM-DD"
                val endDate = event.travelEndDate.substring(0, 10) // "YYYY-MM-DD"
                startDate <= selectedDateStr && endDate >= selectedDateStr
            }

            // ✅ 선택한 날짜의 게시글 필터링
            val postsForDate = lastFetchedPosts.filter { it.postDate == selectedDateStr }

            // ✅ 일정 또는 게시글이 있으면 다이얼로그 띄우기
            if (eventsForDate.isNotEmpty() || postsForDate.isNotEmpty()) {
                showScheduleBottomSheet(eventsForDate, postsForDate)
            }
        })
    }
    /**
     * ✅ Fab 버튼에서 스케줄 버튼 클릭 시 실행되는 함수
     * 선택한 날짜를 Intent로 `ScheduleActivity`에 전달
     */
    private fun openScheduleActivity() {
        val intent = Intent(requireContext(), ScheduleActivity::class.java).apply {
            putExtra("selected_date", "${selectedDate.year}.${selectedDate.month}.${selectedDate.day}")
        }
        startActivity(intent)
    }
    private fun updateMonthTabs(selectedMonth: Int) {
        binding.monthTabs.children.forEachIndexed { index, view ->
            val monthTextView = view as TextView
            val monthText = months[index]  // JAN, FEB, ...

            if (index == selectedMonth) {
                // ✅ 선택된 월 (검은색 + 굵은 글씨 + 초록색 밑줄)
                val spannableString = SpannableString(monthText).apply {
                    setSpan(ForegroundColorSpan(Color.BLACK), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(ThickGreenUnderlineSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // ✅ 새로운 밑줄 적용
                }
                monthTextView.text = spannableString
                monthTextView.setTypeface(null, Typeface.BOLD)
                monthTextView.textSize = 18f
            } else {
                // ✅ 기본 월 스타일 (회색, 일반 글씨)
                monthTextView.text = monthText
                monthTextView.setTextColor(Color.parseColor("#61646B"))
                monthTextView.setTypeface(null, Typeface.NORMAL)
                monthTextView.textSize = 14f
            }
        }
    }


    /**
     * ✅ 연도 및 월을 업데이트하는 함수
     */
    private fun updateYearAndMonth(year: Int, month: Int) {
        binding.yearText.text = year.toString()

        selectedMonth = month - 1  // ✅ 선택된 월 업데이트
        updateMonthTabs(selectedMonth)  // ✅ 월 UI 갱신

        scrollToMonth(selectedMonth) // ✅ 스크롤 맞추기
    }

    /**
     * ✅ 선택된 월이 `HorizontalScrollView`의 중앙에 오도록 자동 스크롤
     */
    private fun scrollToMonth(monthIndex: Int) {
        val monthView = binding.monthTabs.getChildAt(monthIndex)
        val scrollToX = monthView.left - (binding.horizontalScrollView.width - monthView.width) / 2
        binding.horizontalScrollView.smoothScrollTo(scrollToX, 0)
    }
    private fun fetchUserCalendarEvents(year: Int, month: Int) {
        if (userTag == null) {
            Log.e("CalendarFragment", "🚨 userTag가 NULL입니다. 일정 조회 불가")
            return
        }

        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1) // 월의 첫째 날 설정
        val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) // 해당 월의 총 일수
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val allEvents = mutableSetOf<CalendarQueryEvent>() // ✅ 중복 방지를 위한 HashSet 사용
        var completedRequests = 0

        Log.d("CalendarFragment", "📅 ${year}년 ${month}월 일정 전체 조회 시작")

        for (day in 1..maxDays) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val formattedDate = sdf.format(calendar.time)

            CalendarRepository.getUserCalendarEvents(userTag!!, formattedDate) { events ->
                if (!isAdded || context == null) {
                    Log.e("CalendarFragment", "🚨 Fragment가 제거됨. UI 업데이트 중단")
                    return@getUserCalendarEvents
                }

                if (events != null) {
                    allEvents.addAll(events)
                    Log.d("CalendarFragment", "✅ ${formattedDate} 일정 조회 성공! 개수: ${events.size}")
                } else {
                    Log.e("CalendarFragment", "❌ ${formattedDate} 일정 조회 실패")
                }

                completedRequests++
                if (completedRequests == maxDays) {
                    requireActivity().runOnUiThread {
                        if (!isAdded || context == null) return@runOnUiThread
                        lastFetchedEvents = allEvents.toList()

                        // ✅ 기존 데코레이터 제거 후 다시 추가
                        binding.calendarView.removeDecorators()
                        binding.calendarView.addDecorator(TodayDecorator(requireContext()))
                        binding.calendarView.addDecorator(CalendarEventsDecorator(lastFetchedEvents))
                        applyPostDecorator(lastFetchedPosts.mapNotNull { post ->
                            val parts = post.postDate.split("-")
                            if (parts.size == 3) {
                                CalendarDay.from(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                            } else {
                                null
                            }
                        }) // ✅ PostDecorator 다시 추가

                        // ✅ 데코레이터 즉시 반영
                        binding.calendarView.invalidateDecorators()
                        Log.d("CalendarFragment", "✅ 모든 일정 조회 완료. UI 반영 완료")

                        // ✅ 혹시라도 바로 반영되지 않을 경우 500ms 후 다시 UI 반영
                        binding.calendarView.postDelayed({
                            Log.d("CalendarFragment", "🔄 일정 추가 후 500ms 후 다시 UI 반영")
                            binding.calendarView.invalidateDecorators()
                        }, 500)
                    }
                }
            }
        }
    }





    private fun showScheduleBottomSheet(events: List<CalendarQueryEvent>, posts: List<PostItem>) {
        val selectedDateFormatted = formatSelectedDate(selectedDate)

        // ✅ 일정 데이터 변환
        val scheduleItems = events.map { event ->
            ScheduleItem(
                travelId = event.travelId,
                title = event.travelTitle,
                period = "${event.travelStartDate.substring(5, 7)}.${event.travelStartDate.substring(8, 10)} ~ ${event.travelEndDate.substring(5, 7)}.${event.travelEndDate.substring(8, 10)}",
                content = event.travelContent
            )
        }.toMutableList()

        // ✅ 일정과 게시글이 모두 없으면 다이얼로그 띄우지 않음
        if (scheduleItems.isEmpty() && posts.isEmpty()) {
            Log.w("CalendarFragment", "🚨 선택한 날짜에 일정과 게시글이 없음. 다이얼로그 열지 않음.")
            return
        }

        // ✅ 다이얼로그 생성 및 띄우기
        val dialog = ScheduleBottomSheetDialog(selectedDateFormatted, scheduleItems, posts) { travelId ->
            deleteSchedule(travelId)
        }

        dialog.show(parentFragmentManager, "ScheduleBottomSheetDialog")
    }




    /**
     * ✅ 선택한 날짜를 "MM.dd E" 형식으로 변환하는 함수
     * (예: 2025-03-01 → "03.01 금")
     */
    private fun formatSelectedDate(date: CalendarDay): String {
        val calendar = Calendar.getInstance()
        calendar.set(date.year, date.month - 1, date.day) // month는 0부터 시작하므로 -1 해줌

        val sdf = SimpleDateFormat("MM.dd E", Locale.getDefault()) // "03.01 금" 형태
        return sdf.format(calendar.time)
    }





    private fun getDayOfWeek(date: String): String {
        val days = listOf("일", "월", "화", "수", "목", "금", "토")
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = sdf.parse(date)!!
        return days[calendar.get(Calendar.DAY_OF_WEEK) - 1]
    }

    private fun deleteSchedule(travelId: Int) {
        CalendarRepository.deleteCalendarEvent(travelId) { success, message ->
            if (success) {
//                Toast.makeText(requireContext(), "일정 삭제 완료", Toast.LENGTH_SHORT).show()
                fetchUserCalendarEvents(selectedDate.year, selectedDate.month) // ✅ 일정 다시 불러오기
            } else {
//                Toast.makeText(requireContext(), "삭제 실패: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("CalendarFragment", "🚀 onViewCreated 실행됨! 일정 자동 조회 시작")

        // ✅ 앱 실행 시, 일정 조회 실행
        if (userTag != null) {
            fetchUserCalendarEvents(CalendarDay.today().year, CalendarDay.today().month) // 일정 조회
            fetchUserPosts() // ✅ 게시글 조회 추가
        }

        // ✅ "calendar_update" 신호를 감지하면 일정 자동 갱신
        setFragmentResultListener("calendar_update") { _, _ ->
            Log.d("CalendarFragment", "🔄 새로운 일정이 추가됨! 일정 다시 조회")

            if (userTag != null) {
                fetchUserCalendarEvents(CalendarDay.today().year, CalendarDay.today().month)

                // ✅ 캘린더 데코레이터 즉시 반영 (UI 스레드에서 실행)
                requireActivity().runOnUiThread {
                    binding.calendarView.invalidateDecorators()
                }

                // ✅ 혹시라도 바로 반영되지 않을 경우 일정 시간 후 다시 반영
                binding.calendarView.postDelayed({
                    Log.d("CalendarFragment", "🔄 일정 추가 후 500ms 후 다시 UI 반영")
                    binding.calendarView.invalidateDecorators()
                }, 500)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("CalendarFragment", "🔄 onResume() 호출됨 → 일정 & 게시글 자동 갱신 실행")

        if (!isAdded || context == null) {
            Log.e("CalendarFragment", "🚨 Fragment가 attach되지 않음. 데이터 조회 중단")
            return
        }

        if (userTag != null) {
            fetchUserCalendarEvents(CalendarDay.today().year, CalendarDay.today().month) // 일정 조회
            fetchUserPosts() // ✅ 게시글도 다시 불러옴

            // ✅ 500ms 후 다시 UI 업데이트 (혹시라도 반영되지 않을 경우)
            binding.calendarView.postDelayed({
                Log.d("CalendarFragment", "🔄 onResume() 후 500ms 뒤 캘린더 UI 반영")
                binding.calendarView.invalidateDecorators()
            }, 500)
        }

        setFragmentResultListener("calendar_update") { _, _ ->
            Log.d("CalendarFragment", "🔄 일정이 삭제됨 → 일정 다시 조회")
            if (userTag != null) {
                fetchUserCalendarEvents(CalendarDay.today().year, CalendarDay.today().month)
            }
        }
    }
    private fun applyPostDecorator(postDates: List<CalendarDay>) {
        requireActivity().runOnUiThread {
            if (!isAdded || context == null) return@runOnUiThread

            val postDecorator = PostDecorator(requireContext(), postDates)

            // ✅ 기존 데코레이터는 제거하지 않고 유지하면서 추가
            binding.calendarView.addDecorator(postDecorator) // 🔹 항상 최상위 유지
            binding.calendarView.invalidateDecorators() // 즉시 반영
        }
    }


    private fun fetchUserPosts() {
        if (userTag == null) return

        PostRepository.getUserPosts(userTag!!) { posts ->
            if (posts != null) {
                lastFetchedPosts = posts.map { post ->
                    PostItem(
                        post.imageURL ?: "",  // ✅ null-safe 처리
                        post.postContent ?: "",  // ✅ null-safe 처리
                        post.postDate?.substring(0, 10) ?: ""  // ✅ null-safe 처리
                    )
                }

                requireActivity().runOnUiThread {
                    applyPostDecorator(lastFetchedPosts.mapNotNull { post ->
                        val parts = post.postDate.split("-")
                        if (parts.size == 3) {
                            CalendarDay.from(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                        } else {
                            null  // ✅ 날짜 형식이 잘못된 경우 제외
                        }
                    })
                }
            }
        }
    }




}