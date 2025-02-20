import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelbox.databinding.ItemFollowBinding
import com.example.travelbox.data.repository.my.FollowerItem

class FollowerAdapter(
    private var followerList: MutableList<FollowerItem>,  // 🔹 FollowerItem 사용
    private val onFollowButtonClick: (FollowerItem) -> Unit  // 🔹 FollowerItem을 콜백으로 전달
) : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

    inner class FollowerViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FollowerItem) {
            with(binding) {
                Glide.with(profileImage.context)
                    .load(item.profileImageUrl)
                    .circleCrop()
                    .into(profileImage)

                textNickname.text = item.nickname
                textUserId.text = item.userId

                btnFollow.apply {
                    text = if (item.isFollowing) "Following" else "Follow"

                    setOnClickListener {
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            onFollowButtonClick(item)
                            item.isFollowing = !item.isFollowing
                            notifyItemChanged(adapterPosition)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding =
            ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(followerList[position])
    }

    override fun getItemCount(): Int = followerList.size

    fun updateList(newList: List<FollowerItem>) {
        followerList.clear()
        followerList.addAll(newList)
        notifyDataSetChanged()
    }
}
