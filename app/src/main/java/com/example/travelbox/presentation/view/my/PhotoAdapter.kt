package com.example.travelbox.presentation.view.my

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R


//데이터
data class PhotoItem(val imageResId: Int, val title: String)

//어댑터
class PhotoAdapter(private val photoList: List<PhotoItem>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    // ViewHolder: 각 아이템의 뷰를 참조하는 클래스
    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.photoImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.photoTitleTextView)
    }

    // 새로운 뷰를 생성할 때 호출되는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    // 아이템에 데이터를 바인딩할 때 호출되는 메서드
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoItem = photoList[position]
        holder.imageView.setImageResource(photoItem.imageResId)
        holder.titleTextView.text = photoItem.title
    }

    // RecyclerView의 아이템 개수를 반환하는 메서드
    override fun getItemCount(): Int = photoList.size
}
