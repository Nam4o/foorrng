package com.tasteguys.foorrng_owner.presentation.location.manage

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.base.collapse
import com.tasteguys.foorrng_owner.presentation.base.expand
import com.tasteguys.foorrng_owner.presentation.databinding.ItemLocationInfoBinding
import com.tasteguys.foorrng_owner.presentation.model.location.RunInfo
import com.tasteguys.foorrng_owner.presentation.model.location.RunLocationInfo
import java.time.DayOfWeek

private const val TAG = "RunLocationAdapter_poorrng"
class RunLocationAdapter(
    private val runLocationList: List<RunLocationInfo>
) : RecyclerView.Adapter<RunLocationAdapter.RunLocationViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunLocationViewHolder {
        return RunLocationViewHolder(
            ItemLocationInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RunLocationViewHolder, position: Int) {
        holder.bind(runLocationList[position])
    }

    override fun getItemCount(): Int {
        return runLocationList.size
    }

    class RunLocationViewHolder(
        private val binding: ItemLocationInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var runInfoAdapter: RunInfoAdapter? = null
        private var isListVisible = false

        fun bind(item: RunLocationInfo) {
            if (binding.rvLocationDays.adapter == null){
                Log.d(TAG, "bind: ")
                setRunInfoAdapter(item.runInfoList)
            }
            setListToggleListener()
            binding.tvLocationAddress.text = item.address
            binding.tvLocationDays.text = getSelectedDaySpannableString(
                item.runInfoList.map { it.day }, binding.tvLocationDays.text.toString()
            )
        }


        // 선택된 요일 텍스트 색상 변환
        private fun getSelectedDaySpannableString(
            runDayList: List<DayOfWeek>, viewString: String
        ): SpannableString {
            val spannableString = SpannableString(viewString)
            DayOfWeek.values().forEachIndexed { i, dayOfWeek ->
                if (runDayList.contains(dayOfWeek)) {
                    spannableString.setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.foorrng_orange_dark
                            )
                        ),
                        i, i + 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else{
                    spannableString.setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.foorrng_orange_light
                            )
                        ),
                        i, i + 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            return spannableString
        }


        // 리스트 토글 리스너
        private fun setListToggleListener() {
            if (isListVisible){
                binding.ivLocationTimeToggle.setImageResource(R.drawable.ic_dropup)
                binding.layoutLocationDays.visibility = View.VISIBLE
            } else {
                binding.ivLocationTimeToggle.setImageResource(R.drawable.ic_dropdown)
                binding.layoutLocationDays.visibility = View.GONE
            }
            binding.ivLocationTimeToggle.setOnClickListener {
                if (isListVisible){
                    binding.ivLocationTimeToggle.setImageResource(R.drawable.ic_dropdown)
                    binding.layoutLocationDays.collapse()
                } else {
                    binding.ivLocationTimeToggle.setImageResource(R.drawable.ic_dropup)
                    binding.layoutLocationDays.expand()
                }
                isListVisible = !isListVisible
            }
        }

        // 리스트 어댑터 설정
        private fun setRunInfoAdapter(runInfoList: List<RunInfo>) {
            if (runInfoAdapter == null){
                runInfoAdapter = RunInfoAdapter(runInfoList)
                Log.d(TAG, "setRunInfoAdapter: ")
            }
            binding.rvLocationDays.adapter = runInfoAdapter
            Log.d(TAG, "setRunInfoAdapter: ${binding.rvLocationDays.adapter}")
        }
    }
}