import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.travelbox.data.repository.my.MyRepository
import com.example.travelbox.databinding.FragmentStoryBinding
import com.example.travelbox.data.repository.my.Post

class StoryFragment : Fragment() {

    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!

    private val postList = mutableListOf<Post>() // 게시글 리스트

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchPosts() // 게시글 데이터를 가져옴
    }


    private fun fetchPosts() {
        MyRepository.fetchPosts { posts ->
            if (posts.isNullOrEmpty()) {
                // 게시글이 없으면 "게시글이 존재하지 않습니다." 문구 표시
                binding.noPostsText.visibility = View.VISIBLE
                binding.recyclerStory.visibility = View.GONE
            } else {
                // 게시글이 있으면 RecyclerView로 표시
                binding.noPostsText.visibility = View.GONE
                binding.recyclerStory.visibility = View.VISIBLE
                postList.clear() // 기존 리스트 초기화
                postList.addAll(posts) // 새로운 게시글 추가
                setupRecyclerView() // RecyclerView 갱신
            }
        }
    }

    private fun setupRecyclerView() {
        val adapter = StoryAdapter(postList)
        binding.recyclerStory.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerStory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
