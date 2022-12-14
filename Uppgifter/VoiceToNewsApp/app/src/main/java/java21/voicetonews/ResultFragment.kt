package java21.voicetonews

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide


class ResultFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_result, container, false)

        for (bean in MainActivity.beanList){
            addArticle(view, bean)
        }

        //Resets companion variables when using backstack
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled){
                    MainActivity.searchTerm = ""
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        })

        return view
    }

    // Adds article item with provided bean data
    private fun addArticle (view: View, bean: ArticleBean){

        // Finds scrollview in parent (fragment_result) layout xml
        val articleContainer = view.findViewById<View>(R.id.articleContainer) as ScrollView

        // Finds linearLayout in parent (articleFeed) layout xml
        val articleFeed = articleContainer.findViewById<View>(R.id.articleFeed) as LinearLayout

        // Identifies article item layout xml
        val article: View = layoutInflater.inflate(R.layout.article_item,null)

        // Author/Source
        val sourceText: TextView = article.findViewById(R.id.articleSource)
        sourceText.text = bean.source

        // Title
        val titleText: TextView = article.findViewById(R.id.articleTitle)
        titleText.text = bean.title

        // Image with Glide library
        val imageBox: ImageView = article.findViewById(R.id.articleImage)
        Glide.with(view).load(bean.imageURL).into(imageBox)
        articleFeed.addView(article)

        // Upload date
        val dateText: TextView = article.findViewById(R.id.articleDate)
        dateText.text = bean.uploadDate
    }
}