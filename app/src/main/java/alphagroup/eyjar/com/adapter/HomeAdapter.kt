package alphagroup.eyjar.com.adapter

import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.model.home.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.car_item_model.view.*
import android.os.Bundle
import androidx.navigation.findNavController


class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder>() {
    private var resultList: List<Data?>? = null
    private lateinit var bundle: Bundle
    fun setList(list: List<Data?>?) {
        this.resultList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_item_model, parent, false)
        return HomeAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resultList!!.size
    }

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        val model: Data? = resultList?.get(position)
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        try {
            Glide.with(holder.itemView).load(model?.image)
                .apply(options)
                .into(holder.carImage)

        } catch (e: Exception) {
            holder.carImage.setImageResource(android.R.color.darker_gray)
        }

        holder.carName.text = model?.model
        holder.price.text = model?.price
        bundle = Bundle()
        holder.itemView.setOnClickListener {
            bundle.putString("carImg", model?.image)
            bundle.putString("title", model?.title)
            bundle.putString("model", model?.model)
            bundle.putString("gearType", model?.gearType)
            bundle.putString("year", model?.year)
            bundle.putString("payWay", it.resources.getString(R.string.cash))
            bundle.putString("enginePower", model?.enginePower)
            bundle.putString("fuelType", model?.fuelType)
            bundle.putString("licencePlate", model?.licancePlate)
            bundle.putString("color", model?.color)
            bundle.putString("ownerName", model?.userName)
            bundle.putString("address", model?.address)
            bundle.putString("price", model?.price)
            bundle.putString("phone", model?.phone)

            it.findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment, bundle
            )


        }

    }

    class HomeAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carName: TextView = itemView.carName
        val carImage: ImageView = itemView.carImage
        val price: TextView = itemView.price

    }
}
