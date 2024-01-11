package com.example.midterm_app

// YourFragment.kt
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midterm_app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Category(
    val id: Int,
    val category: String,
    val time: String
)

interface ApiService {
    @GET("smarttrashbin/getcategory.php")
    fun getCategories(): Call<List<Category>>
}

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categories: List<Category> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textId: TextView = itemView.findViewById(R.id.textId)
        val textCategory: TextView = itemView.findViewById(R.id.textCategory)
        val textTime: TextView = itemView.findViewById(R.id.textTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_f_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.textId.text = "ID: ${category.id}"
        holder.textCategory.text = "Category: ${category.category}"
        holder.textTime.text = "Time: ${category.time}"
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setData(newData: List<Category>) {
        categories = newData
        notifyDataSetChanged()
    }
}

class fHistory : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_f_history, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        categoryAdapter = CategoryAdapter()
        recyclerView.adapter = categoryAdapter

        fetchData()

        return view
    }

    private fun fetchData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://smarttrashbin188.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getCategories()

        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        categoryAdapter.setData(it)
                    }
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
