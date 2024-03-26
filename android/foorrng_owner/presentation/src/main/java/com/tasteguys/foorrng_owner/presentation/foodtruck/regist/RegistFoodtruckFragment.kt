package com.tasteguys.foorrng_owner.presentation.foodtruck.regist

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentRegistFoodtruckBinding
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.main.MainToolbarControl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistFoodtruckFragment : MainBaseFragment<FragmentRegistFoodtruckBinding>(
    FragmentRegistFoodtruckBinding::bind, R.layout.fragment_regist_foodtruck
) {
    private val registFoodtruckViewModel: RegistFoodtruckViewModel by viewModels()

    private var menuCategoryAdapter: MenuCategoryAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryView()
        setCategoryViewAdapter(dummyCategory.sortedBy { it.length })


        mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            checkBackStackDialog()
        }
    }

    private fun regist(){

    }

    private fun validateInput(): Result<String> {
        val nameValidation = binding.tilFoodtruckName.editText!!.text.toString().isNotBlank()

        return Result.failure(Exception("이름을 입력해주세요"))
    }

    override fun setToolbar() {
        MainToolbarControl(
            visible = true,
            title = resources.getString(R.string.regist_foodtruck_title),
            menuRes = R.menu.menu_check
        ).addMenuItemClickListener {
            showToast("등록 완료")
        }.addNavIconClickListener {
            checkBackStackDialog()
        }.also {
            mainViewModel.changeToolbar(it)
        }
    }

    private fun checkBackStackDialog(){
        MaterialAlertDialogBuilder(_activity)
            .setTitle("등록을 취소하시겠습니까?")
            .setMessage("작성 중인 내용이 모두 삭제됩니다.")
            .setPositiveButton("확인") { _, _ ->
                parentFragmentManager.popBackStack()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun initCategoryView(){
        binding.rvMenuCategory.layoutManager = FlexboxLayoutManager(_activity)
    }

    private fun setCategoryViewAdapter(menuList: List<String>){
        if (menuCategoryAdapter == null){
            menuCategoryAdapter = MenuCategoryAdapter(menuList)
        }
        binding.rvMenuCategory.adapter = menuCategoryAdapter
    }

    private val dummyCategory = listOf("덮밥", "전기구이통닭", "꼬치", "타코야끼", "타코 & 케밥", "분식(떡볶이 만두 순대 어묵 튀김)", "빵(국화빵 붕어빵 호떡)", "구황작물(군고구마, 찐옥수수, 군밤)", "카페 & 디저트", "기타")
}