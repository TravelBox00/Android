import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.data.repository.auth.AuthRepository
import com.example.travelbox.databinding.FragmentInformationBinding
import com.example.travelbox.presentation.view.IntroActivity
import com.example.travelbox.presentation.view.user.LoginActivity

class InformationFragment : Fragment() {

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)

        binding.logoutBtn.setOnClickListener {
            AuthRepository.logout { isSuccess ->
                requireActivity().runOnUiThread {
                    if (isSuccess) {
                        val intent = Intent(requireContext(), IntroActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), "로그아웃 실패, 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
