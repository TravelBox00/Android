import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.databinding.FragmentCommentBinding
import com.example.travelbox.presentation.view.my.adapter.Comment
import com.example.travelbox.presentation.view.my.adapter.CommentAdapter

class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

    // Comment 데이터 리스트
    private val commentList = listOf(
        Comment(
            userId = "USER1",
            commentText = "댓글 내용입니다.",
            profileImageUrl = "https://example.com/user1.jpg",
            myId = "USER2",
            myCommentText = "댓글 내용",
            myProfileImageUrl = "https://example.com/user2.jpg"  // URL
        ),
        Comment(
            userId = "USER3",
            commentText = "다른 댓글",
            profileImageUrl = "https://example.com/user3.jpg",  //URL
            myId = "USER4",
            myCommentText = "응답 댓글",
            myProfileImageUrl = "https://example.com/user4.jpg"  //URL
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = CommentAdapter(commentList, onEdit = { position ->
            // 댓글 수정 처리 로직
        }, onDelete = { position ->
            // 댓글 삭제 처리 로직
        })
        binding.recyclerComments.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerComments.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
