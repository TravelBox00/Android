import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelbox.databinding.ItemFollowBinding
import com.example.travelbox.data.repository.my.FollowerItem
import com.example.travelbox.data.repository.my.FollowingItem

class FollowingAdapter(
    private var followList: MutableList<FollowingItem>,  // 🔹 FollowerItem → FollowingItem 으로 변경
    private val onFollowButtonClick: (FollowingItem) -> Unit  // 🔹 타입 수정
) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    inner class FollowingViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FollowingItem) {  // 🔹 타입 수정
            with(binding) {
                Glide.with(profileImage.context)
                    .load(item.profileImageUrl)
                    .circleCrop()
                    .into(profileImage)

                textNickname.text = item.userId

                btnFollow.apply {
                    text = if (item.isFollowedByThem) "Following" else "Follow"

                    setOnClickListener {
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            onFollowButtonClick(item)
                            item.isFollowedByThem = !item.isFollowedByThem
                            notifyItemChanged(adapterPosition)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding =
            ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(followList[position])
    }

    override fun getItemCount(): Int = followList.size

    fun updateList(newList: List<FollowingItem>) {
        followList.clear()
        followList.addAll(newList)
        notifyDataSetChanged()
    }
}
