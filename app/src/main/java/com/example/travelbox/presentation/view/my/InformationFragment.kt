import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.travelbox.data.repository.my.MyRepository
import com.example.travelbox.data.repository.my.UserInfo
import com.example.travelbox.databinding.FragmentInformationBinding
import com.example.travelbox.presentation.view.IntroActivity

class InformationFragment : Fragment() {

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)

        loadUserInfo()

        binding.logoutBtn.setOnClickListener {
            logout()
        }

        return binding.root
    }

    private fun loadUserInfo() {
        MyRepository.getUserInfo { userInfo ->
            if (userInfo != null) {
                updateUI(userInfo)
            } else {
                Toast.makeText(requireContext(), "사용자 정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(userInfo: UserInfo) {
        binding.textNickname.setText(userInfo.userNickname)
        binding.textName.setText(userInfo.userNickname)
        binding.textId.setText(userInfo.userTag)
        Glide.with(this)
            .load(userInfo.userProfileImage)
            .into(binding.profileImage)
    }

    private fun logout() {
        // 로그아웃 처리
        val intent = Intent(requireContext(), IntroActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
