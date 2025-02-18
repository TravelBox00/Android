package com.example.travelbox.presentation.view.post

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import java.io.File
import java.io.FileOutputStream

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

    private lateinit var regionAdapter: ButtonAdapter
    //private lateinit var hashtagAdapter: ButtonAdapter

    private val regionList = mutableListOf<String>()
    //private val hashtagList = mutableListOf<String>()

    private var postRegionCode = ""

    private var selectedCategory: String? = null

    // 여러 개의 이미지 URI 저장
    private val imageUris = mutableListOf<Uri>()

    // 여러 개의 이미지 선택을 위한 Launcher
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
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

        // 카테고리 버튼 클릭 리스너
        val categoryButtons = listOf(
            binding.btnCategory1,
            binding.btnCategory2,
            binding.btnCategory3,
            binding.btnCategory4
        )
        categoryButtons.forEach { button ->
            button.setOnClickListener {
                val clicked = updateButtonState(button, button.tag as? Boolean ?: false)
                button.tag = clicked
                selectedCategory = button.text.toString()
            }
        }

        setupRegionButtons()

        // 이미지 선택 버튼 클릭 리스너
        binding.ivPhoto1.setOnClickListener {
            pickImageFromGallery()
        }

        // 업로드 버튼 클릭 리스너
        binding.btnUpload.setOnClickListener {
            onAddPostClick()
        }
    }

    // 카테고리 버튼 클릭 상태 변경
    private fun updateButtonState(button: Button, clicked: Boolean): Boolean {
        if (clicked) {
            button.setTextColor(Color.parseColor("#ACACAC"))
            button.setBackgroundResource(R.drawable.btn_category)
        } else {
            button.setTextColor(Color.parseColor("#007151"))
            button.setBackgroundResource(R.drawable.btn_create)
        }
        return !clicked
    }

    // 이미지 간 레이아웃 조정
    private fun adjustImageLayout() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.photoContainer)

        constraintSet.clear(binding.ivPhoto1.id, ConstraintSet.END)
        constraintSet.clear(binding.ivPhoto1.id, ConstraintSet.START)
        constraintSet.clear(binding.ivPhoto2.id, ConstraintSet.END)
        constraintSet.clear(binding.ivPhoto2.id, ConstraintSet.START)

        constraintSet.connect(binding.ivPhoto1.id, ConstraintSet.END, binding.ivPhoto2.id, ConstraintSet.START, 4)
        constraintSet.connect(binding.ivPhoto2.id, ConstraintSet.START, binding.ivPhoto1.id, ConstraintSet.END, 4)

        constraintSet.connect(binding.ivPhoto1.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(binding.ivPhoto2.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

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

    // 사진 압축 (임시)
    private fun compressImageFile(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        val compressedFile = File.createTempFile("compressed_", ".jpg", requireContext().cacheDir)
        val outputStream = FileOutputStream(compressedFile)

        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        outputStream.flush()
        outputStream.close()

        return compressedFile
    }

    // URI에서 파일로 변환
    private fun getSelectedFiles(): List<File> {
        return imageUris.mapNotNull { uri ->
            getFileFromUri(uri)?.let { compressImageFile(it) }
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

    // 게시글 업로드 버튼 클릭 시 호출
    private fun onAddPostClick() {
        val userTag = "testuser1234"
        val postCategory = selectedCategory ?: return
        val songName = binding.etSong.text.toString().trim()
        val postContent = binding.etInfo.text.toString().trim()
        val clothId = binding.etUri.text.toString().trim().toIntOrNull() ?: return

        val files = getSelectedFiles()

        Log.d("AddPost", "userTag: $userTag")
        Log.d("AddPost", "postCategory: $postCategory")
        Log.d("AddPost", "postRegionCode: $postRegionCode")
        Log.d("AddPost", "songName: $songName")
        Log.d("AddPost", "postContent: $postContent")
        Log.d("AddPost", "clothId: $clothId")
        Log.d("AddPost", "파일 개수: ${files.size}")

        addPostRepository.addPost(
            userTag, postCategory, postRegionCode, songName, postContent, clothId, files
        ) { response ->
            if (response?.isSuccess == true) {
                Log.d("AddPost", "게시글 업로드 성공: ${response.message}")
                Toast.makeText(requireContext(), "게시글 업로드 성공", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("AddPost", "게시글 업로드 실패: ${response?.message}")
                Toast.makeText(requireContext(), "게시글 업로드 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}