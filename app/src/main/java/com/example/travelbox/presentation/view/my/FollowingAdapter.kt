import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelbox.databinding.ItemFollowBinding

data class FollowItem(
    val profileImageUrl: String,
    val nickname: String,
    val userId: String,
    var isFollowing: Boolean
)


class FollowingAdapter(
    private val followList: List<FollowItem>,
    private val onFollowButtonClick: (FollowItem) -> Unit
) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    inner class FollowingViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FollowItem) {
            with(binding) {
                Glide.with(profileImage.context)
                    .load(item.profileImageUrl)
                    .circleCrop()
                    .into(profileImage)

                textNickname.text = item.nickname
                textUserId.text = item.userId

                btnFollow.apply {
                    text = if (item.isFollowing) "Following" else "Follow"
                    setBackgroundTintList(
                        context.getColorStateList(
                            if (item.isFollowing) android.R.color.darker_gray else android.R.color.holo_blue_light
                        )
                    )

                    setOnClickListener {
                        onFollowButtonClick(item)  // 여기서 클릭 시 액션을 수행
                        item.isFollowing = !item.isFollowing
                        notifyItemChanged(adapterPosition)
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
}
