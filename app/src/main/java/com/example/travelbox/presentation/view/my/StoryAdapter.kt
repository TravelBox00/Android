import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.databinding.ItemStoryBinding
import com.bumptech.glide.Glide
import com.example.travelbox.data.repository.my.Post

class StoryAdapter(private val posts: List<Post>) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val post = posts[position]
        // 이미지 URL을 Glide를 통해 표시
        Glide.with(holder.itemView.context)
            .load(post.imageUrl) // Post에 포함된 이미지 URL
            .into(holder.binding.storyImage)
    }

    override fun getItemCount(): Int = posts.size

    inner class StoryViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)
}
