package com.example.covid_19tracker.newsBlog

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.covid_19tracker.databinding.ActivityNewsBinding
import com.example.covid_19tracker.databinding.ActivityNewsItemListBinding
import com.example.covid_19tracker.vaccineSlot.VolleySingleton

class NewsActivity : AppCompatActivity(), NewsItemClicked{

    private lateinit var binding: ActivityNewsBinding
    private lateinit var bindingNIL: ActivityNewsItemListBinding
    private lateinit var mAdapter: NewsListAdapter
    companion object {
        const val NEWS_URL = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)

        binding.recyclerView.adapter = mAdapter

    }

    private fun fetchData() {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, NEWS_URL, null,
            {
                val newsJSONArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJSONArray.length()) {
                    val newsJsonObject = newsJSONArray.getJSONObject(i)
                    val sourceJsonObject = newsJsonObject.getJSONObject("source")

                    val news = News(
                        newsJsonObject.getString("title"),
                        sourceJsonObject.getString("name"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                mAdapter.updateNews(newsArray)
            },
            {
                Toast.makeText(this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show()
            }
        )

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}