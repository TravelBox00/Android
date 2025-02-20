package com.example.travelbox.presentation.view.post

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintSet
import com.example.travelbox.R
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.post.AddPostInterface
import com.example.travelbox.data.repository.post.AddPostRepository
import com.example.travelbox.databinding.FragmentAddPostBinding
import com.example.travelbox.presentation.view.search.SearchPostFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPostFragment : Fragment() {

    lateinit var binding: FragmentAddPostBinding

    private lateinit var addPostRepository: AddPostRepository
    private lateinit var addPostInterface: AddPostInterface

    private var selectedButton: Button? = null
    private var selectedCategory: String? = null

    private lateinit var regionAdapter: ButtonAdapter
    //private lateinit var hashtagAdapter: ButtonAdapter

    private val regionList = mutableListOf<String>()
    //private val hashtagList = mutableListOf<String>()

    private var postRegionCode = ""

    // 여러 개의 이미지 URI 저장
    private val imageUris = mutableListOf<Uri>()

    // 여러 개의 이미지 선택을 위한 Launcher
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (uris.isNotEmpty()) {
                imageUris.clear()
                imageUris.addAll(uris)
                Log.d("AddPost", "선택된 이미지 개수: ${imageUris.size}")
                binding.ivPhoto2.visibility = View.VISIBLE
                binding.ivPhoto2.setImageURI(imageUris.first()) // 첫 번째 이미지만
                adjustImageLayout()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPostInterface = ApiNetwork.createService(AddPostInterface::class.java)
        addPostRepository = AddPostRepository(addPostInterface)
        // 뒤로가기 버튼 클릭 이벤트 추가
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()  // 이전 Fragment로 이동
        }
        // 카테고리 버튼 클릭 리스너
        val categoryButtons = listOf(
            binding.btnCategory1,
            binding.btnCategory2,
            binding.btnCategory3,
            binding.btnCategory4
        )
        categoryButtons.forEach { button ->
            button.setOnClickListener {
                if (selectedButton == button) {
                    // 같은 버튼을 다시 누른 경우 선택 해제
                    resetButtonState(button)
                    selectedButton = null
                    selectedCategory = null

                    binding.tvClothes.visibility = View.GONE
                    binding.etUri.visibility = View.GONE
                } else {
                    // 기존 선택 버튼 초기화
                    selectedButton?.let { resetButtonState(it) }

                    // 선택된 버튼 상태 업데이트
                    updateButtonState(button)
                    selectedButton = button
                    selectedCategory = button.text.toString()

                    // btnCategory4(여행코디)가 선택된 경우에만 옷 정보 영역 보이기
                    if (button == binding.btnCategory4) {
                        binding.tvClothes.visibility = View.VISIBLE
                        binding.etUri.visibility = View.VISIBLE
                    } else {
                        binding.tvClothes.visibility = View.GONE
                        binding.etUri.visibility = View.GONE
                    }
                }
            }
        }

        setupRegionButtons()

        // 이미지 선택 버튼 클릭 리스너
        binding.ivPhoto1.setOnClickListener {
            pickImageFromGallery()
        }

        // 노래 검색 버튼 클릭 리스너
        binding.btnAddSong.setOnClickListener {
            getSpotifySongUrl()
        }

        // 업로드 버튼 클릭 리스너
        binding.btnUpload.setOnClickListener {
            onAddPostClick()
        }
    }

    // 카테고리 버튼 상태를 선택된 상태로 변경
    private fun updateButtonState(button: Button) {
        button.setTextColor(Color.parseColor("#007151"))
        button.setBackgroundResource(R.drawable.btn_create)
    }

    // 카테고리 버튼 상태를 기본 상태로 초기화
    private fun resetButtonState(button: Button) {
        button.setTextColor(Color.parseColor("#ACACAC"))
        button.setBackgroundResource(R.drawable.btn_category)
    }

    // 이미지 간 레이아웃 조정
    private fun adjustImageLayout() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.photoContainer)

        constraintSet.clear(binding.ivPhoto1.id, ConstraintSet.END)
        constraintSet.clear(binding.ivPhoto1.id, ConstraintSet.START)
        constraintSet.clear(binding.ivPhoto2.id, ConstraintSet.END)
        constraintSet.clear(binding.ivPhoto2.id, ConstraintSet.START)

        constraintSet.connect(
            binding.ivPhoto1.id,
            ConstraintSet.END,
            binding.ivPhoto2.id,
            ConstraintSet.START,
            4
        )
        constraintSet.connect(
            binding.ivPhoto2.id,
            ConstraintSet.START,
            binding.ivPhoto1.id,
            ConstraintSet.END,
            4
        )

        constraintSet.connect(
            binding.ivPhoto1.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            binding.ivPhoto2.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )

        constraintSet.applyTo(binding.photoContainer)
    }

    // Region 버튼 설정
    private fun setupRegionButtons() {
        val regionLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
        }
        binding.recyclerViewRegions.layoutManager = regionLayoutManager

//        val hashtagLayoutManager = FlexboxLayoutManager(requireContext()).apply {
//            flexWrap = FlexWrap.WRAP
//            flexDirection = FlexDirection.ROW
//        }
//        binding.recyclerViewHashtags.layoutManager = hashtagLayoutManager

        regionAdapter = ButtonAdapter(regionList)
        binding.recyclerViewRegions.adapter = regionAdapter

        //hashtagAdapter = ButtonAdapter(hashtagList)
        //binding.recyclerViewHashtags.adapter = hashtagAdapter

        // 지역 추가 기능
        binding.btnAddRegion.setOnClickListener {
            val regionText = binding.etRegion.text.toString().trim()
            if (regionText.isNotEmpty() && !regionList.contains(regionText)) {
                regionList.add(regionText)
                regionAdapter.notifyItemInserted(regionList.size - 1)
                postRegionCode = regionList.joinToString(" ")
                binding.etRegion.text.clear()
            }
        }

//        binding.btnAddHashtag.setOnClickListener {
//            val hashtagText = binding.etHashtag.text.toString().trim()
//            if (hashtagText.isNotEmpty() && !hashtagList.contains(hashtagText)) {
//                hashtagList.add("#$hashtagText")
//                hashtagAdapter.notifyItemInserted(hashtagList.size - 1)
//                binding.etHashtag.text.clear()
//            }
//        }
    }

    // 갤러리에서 이미지 선택
    private fun pickImageFromGallery() {
        imagePickerLauncher.launch("image/*")
    }

    // URI에서 파일로 변환
    private fun getSelectedFiles(): List<File> {
        return imageUris.mapNotNull { uri ->
            getFileFromUri(uri)
        }
    }

    private fun getFileFromUri(uri: Uri): File? {
        val context = requireContext()
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return tempFile
    }

    // Spotify 노래 Url 얻기
    private fun getSpotifySongUrl() {
        val songName = binding.etSong.text.toString().trim()
        if (songName.isNotEmpty()) {
            addPostRepository.getSpotifySong(songName) { songUrl ->
                requireActivity().runOnUiThread {
                    if (songUrl != null) {
                        binding.etSong.setText(songUrl)
                    } else {
                        Toast.makeText(requireContext(), "노래를 찾을 수 없습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "노래 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    // 게시글 업로드 버튼 클릭 시 호출
    private fun onAddPostClick() {
        val userTag = ApiNetwork.getUserTag() ?: run {
            Toast.makeText(requireContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val postCategory = selectedCategory ?: return
        postRegionCode = regionList.joinToString(" ")
        val songName = binding.etSong.text.toString().trim()
        val postContent = binding.etInfo.text.toString().trim()
        val clothInfo = binding.etUri.text.toString().trim()

        val files = getSelectedFiles()

        Log.d("AddPost", "userTag: $userTag")
        Log.d("AddPost", "postCategory: $postCategory")
        Log.d("AddPost", "postRegionCode: $postRegionCode")
        Log.d("AddPost", "songName: $songName")
        Log.d("AddPost", "postContent: $postContent")
        Log.d("AddPost", "clothInfo: $clothInfo")
        Log.d("AddPost", "파일 개수: ${files.size}")

        addPostRepository.addPost(
            userTag, postCategory, postRegionCode, songName, postContent, clothInfo, files
        ) { response ->
            requireActivity().runOnUiThread {
                if (response?.success == true) {
                    Toast.makeText(requireContext(), "게시글 업로드 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "게시글 업로드 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 이전 화면으로 돌아가기
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frm, PostFragment())
        transaction.commit()
    }
}