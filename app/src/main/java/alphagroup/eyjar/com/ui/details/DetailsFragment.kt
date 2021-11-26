package alphagroup.eyjar.com.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.main_toolbar.view.*

import android.content.Intent

import android.net.Uri


class DetailsFragment : Fragment(), View.OnClickListener {
    private val TAG: String = DetailsFragment::class.java.simpleName
    private lateinit var phoneTxt:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        toolbar.backBtn.visibility = View.VISIBLE
        toolbar.backBtn.setOnClickListener(this)
        waBtn.setOnClickListener(this)
        callBtn.setOnClickListener(this)

        initViews()

    }

    private fun initViews() {
        val carImgTxt = arguments?.getString("carImg").toString()
        val titleTxt = arguments?.getString("title").toString()
        val modelTxt = arguments?.getString("model").toString()
        val gearTypeTxt = arguments?.getString("gearType").toString()
        val yearTxt = arguments?.getString("year").toString()
        val payWayTxt = arguments?.getString("payWay").toString()
        val enginePowerTxt = arguments?.getString("enginePower").toString()
        val fuelTypeTxt = arguments?.getString("fuelType").toString()
        val licencePlateTxt = arguments?.getString("licencePlate").toString()
        val colorTxt = arguments?.getString("color").toString()
        val ownerNameTxt = arguments?.getString("ownerName").toString()
        val addressTxt = arguments?.getString("address").toString()
        val priceTxt = arguments?.getString("price").toString()
        phoneTxt = arguments?.getString("phone").toString()

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        try {
            Glide.with(requireActivity()).load(carImgTxt)
                .apply(options)
                .into(carImg)

        } catch (e: Exception) {
            carImg.setImageResource(android.R.color.darker_gray)
        }
        title.text = titleTxt
        model.text = modelTxt
        gearType.text = gearTypeTxt
        year.text = yearTxt
        payWay.text = payWayTxt
        enginePower.text = enginePowerTxt
        fuelType.text = fuelTypeTxt
        licencePlate.text = licencePlateTxt
        color.text = colorTxt
        ownerName.text = ownerNameTxt
        address.text = addressTxt
        price.text = priceTxt


    }

    override fun onClick(view: View?) {
        if (toolbar.backBtn == view) {
            requireActivity().onBackPressed()

        }

        if (callBtn == view) {
            val mobileNumber = "+966544438683"
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL
            intent.data = Uri.parse("tel: $mobileNumber")
            startActivity(intent)

        }
        if (waBtn == view) {
          openWhatsApp("+966544438683")
        }

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openWhatsApp(number: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$number/?text=")
            startActivity(intent)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}