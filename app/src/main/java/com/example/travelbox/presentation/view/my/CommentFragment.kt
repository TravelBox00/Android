import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.data.repository.my.Comment
import com.example.travelbox.data.repository.my.MyRepository
import com.example.travelbox.databinding.FragmentCommentBinding
import com.example.travelbox.presentation.view.my.adapter.CommentAdapter

class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

    private val commentList = mutableListOf<Comment>()

    private val adapter = CommentAdapter(commentList, onEdit = { position ->
        // 댓글 수정 처리 로직
    }, onDelete = { position ->
        // 댓글 삭제 처리 로직
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)

        setupRecyclerView()

        // 서버에서 댓글을 가져옵니다.
        fetchComments()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerComments.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerComments.adapter = adapter
    }

    private fun fetchComments() {
        MyRepository.fetchComments { comments ->
            if (comments != null) {
                // 댓글 데이터를 리스트에 추가하고 RecyclerView 갱신
                commentList.clear()
                commentList.addAll(comments)
                adapter.notifyDataSetChanged()

                // 댓글이 없을 경우 "작성한 댓글이 없습니다." 텍스트 표시
                if (commentList.isEmpty()) {
                    binding.noCommentsMessage.visibility = View.VISIBLE
                } else {
                    binding.noCommentsMessage.visibility = View.GONE
                }
            } else {
                // 실패 시 메시지 처리
                Log.e("CommentFragment", "댓글을 불러오는데 실패했습니다.")
                binding.noCommentsMessage.visibility = View.VISIBLE  // 실패 시에도 메시지 표시
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
