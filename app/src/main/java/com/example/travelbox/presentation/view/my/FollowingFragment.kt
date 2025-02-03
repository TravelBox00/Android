import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    // 불변 리스트에서 가변 리스트로 변경
    private val itemList = mutableListOf(
        FollowItem("https://example.com/user1.jpg", "이름1", "user1", false),
        FollowItem("https://example.com/user2.jpg", "이름2", "user2", true)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        // 어댑터를 setupRecyclerView() 내에서 초기화
        val adapter = FollowingAdapter(itemList) { followItem ->
            // 버튼 클릭 시 isFollowing 값을 반전
            followItem.isFollowing = !followItem.isFollowing
            // 데이터가 변경되었으므로 RecyclerView에 변경 알리기
            //adapter.notifyDataSetChanged()
        }

        // RecyclerView에 어댑터와 레이아웃 매니저 설정
        binding.recyclerFollowing.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFollowing.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
