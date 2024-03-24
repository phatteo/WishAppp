package com.example.b2004814_nguyenthanhtin_it.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.b2004814_nguyenthanhtin_it.R
import com.example.b2004814_nguyenthanhtin_it.WishListFragment
import com.example.b2004814_nguyenthanhtin_it.apis.Constants
import com.example.b2004814_nguyenthanhtin_it.databinding.FragmentAddBinding
import com.example.b2004814_nguyenthanhtin_it.models.RequestAddWish
import com.example.b2004814_nguyenthanhtin_it.sharedpreferences.AppSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var idUser = ""
    private var fullName = ""
    private var content = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)

        //khoi tao mAppSharedPreferences va lay idUSer tu do ra
        mAppSharedPreferences = AppSharedPreferences(requireContext())
        idUser = mAppSharedPreferences.getIdUser("idUser").toString()

        binding.apply {
            btnSave.setOnClickListener {
                if(edtFullName.text.isNotEmpty() && edtContent.text.isNotEmpty()) {
                    fullName = edtFullName.text.toString().trim()
                    content = edtContent.text.toString().trim()
                    //call api them dieu uoc
                    addWish(fullName, content)
                }
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun addWish(fullName: String, content: String) {
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val resp = Constants.getInstance()

                    .addWish(RequestAddWish(idUser, fullName, content))
                    .body()

                if(resp != null) {
                    if(resp.success) {
                        binding.progressBar.visibility = View.GONE
                        //them dieu uoc thanh cong
                        Toast.makeText(requireContext(), resp.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, WishListFragment())
                            .commit()
                    }
                    else {
                        binding.progressBar.visibility = View.GONE
                        //them dieu uoc khong thanh cong
                        Toast.makeText(requireContext(), resp.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, LoginFragment())
                            .commit()
                    }
                }
            }
        }
    }
}