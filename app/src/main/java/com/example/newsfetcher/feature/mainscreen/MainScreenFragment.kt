package com.example.newsfetcher.feature.mainscreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfetcher.R
import com.example.newsfetcher.feature.articleInfoFragment.ArticleInfoFragment
import com.example.newsfetcher.feature.domain.ArticleModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {

    private val viewModel : MainScreenViewModel by viewModel()
    private val recyclerView: RecyclerView by lazy {requireActivity().findViewById (R.id.rvArticles) }
    private val ivSearch: ImageView by lazy {requireActivity().findViewById (R.id.ivSearch) }
    private val tvTittle: TextView by lazy {requireActivity().findViewById (R.id.tvTittle) }

    private val etSearch: EditText by lazy {requireActivity().findViewById (R.id.etSearch) }
    private val adapter: ArticleAdapter by lazy {
        ArticleAdapter (
            { index ->  viewModel.processUiEvent(UiEvent.OnArticleClicked(index))},
            {currentArticle -> openArticle(currentArticle)}
        )
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe (viewLifecycleOwner, :: render)
        recyclerView.adapter=adapter

        ivSearch.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnSearchButtonClicked)
        }

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                viewModel.processUiEvent(UiEvent.OnSearchEdit(text.toString()))
            }

        })

    }


    private fun render (viewState: ViewState) {
        tvTittle.isVisible =!viewState.isSearchEnabled
        etSearch.isVisible = viewState.isSearchEnabled
        adapter.setData(viewState.articlesShown)
    }

    private fun openArticle(currentArticle: ArticleModel) {
        val bundle = Bundle()
        bundle.putString("title",currentArticle.title)
        bundle.putString("author",currentArticle.author)
        bundle.putString("url",currentArticle.url)
        bundle.putString("content",currentArticle.content)
        bundle.putString("description",currentArticle.description)
        bundle.putString("publishedAt",currentArticle.publishedAt)
        bundle.putString("urlToImage",currentArticle.urlToImage)
        parentFragmentManager.beginTransaction().replace(
            R.id.container, ArticleInfoFragment.getNewInstance(bundle)).commit()
    }

}