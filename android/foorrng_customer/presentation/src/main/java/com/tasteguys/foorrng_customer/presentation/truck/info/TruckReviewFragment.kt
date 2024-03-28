package com.tasteguys.foorrng_customer.presentation.truck.info

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.tasteguys.foorrng_customer.presentation.Dummy
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseFragment
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentTruckReviewBinding
import com.tasteguys.foorrng_customer.presentation.model.mapper.toReview
import com.tasteguys.foorrng_customer.presentation.truck.TruckViewModel
import com.tasteguys.foorrng_customer.presentation.truck.info.adapter.TruckReviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log


private const val TAG = "TruckReviewFragment"
@AndroidEntryPoint
class TruckReviewFragment(private val truckId: Long) : BaseFragment<FragmentTruckReviewBinding>(
    { FragmentTruckReviewBinding.bind(it)}, R.layout.fragment_truck_review
) {

    private val truckViewModel: TruckViewModel by activityViewModels()
    private val truckReviewAdapter = TruckReviewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        registerObserve()
    }

    private fun initView(){
        with(binding){
            tvTotalCnt.text = Dummy.truckInfo.review.total.toString()
            rvReview.apply {
                adapter = truckReviewAdapter
            }
            btnWriteReview.setOnClickListener {
                requireParentFragment().parentFragmentManager.beginTransaction()
                    .replace(R.id.fcv_container, TruckWriteReviewFragment(truckId))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun registerObserve(){
        truckViewModel.truckDetailResult.observe(viewLifecycleOwner){res->
            if(res.isSuccess){
                val data = res.getOrNull()!!
                val total = data.totalReview
                binding.tvTotalCnt.text = total.toString()
                val reviews = data.reviews.map{it.toReview(total)}
                truckReviewAdapter.submitList(reviews)
            }
        }
    }

}