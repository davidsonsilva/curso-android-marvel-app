package com.example.marvelapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.extensions.showLongToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding :FragmentDetailBinding ? = null
    private val binding  :FragmentDetailBinding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewArgs = args.detailViewArgs
        binding.imageCharacter.run {
            transitionName = detailViewArgs.name
            imageLoader.load(this, detailViewArgs.imageUrl)
        }
        setSharedElementTransitionOnEnter()

        loadCategoriesObserveUiState(detailViewArgs)

        setObserveFavoriteUiState(detailViewArgs)

    }

    private fun loadCategoriesObserveUiState(detailViewArgs: DetailViewArgs) {
        viewModel.categories.load(detailViewArgs.characterId)
        viewModel.categories.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperDetail.displayedChild =  when(uiState){
                UiActionStateLiveData.UiState.Loading -> {
                    FLIPPER_CHILD_POSITION_LOADING
                }
                is UiActionStateLiveData.UiState.Success -> {
                    binding.recyclerParentDetail.run {
                        setHasFixedSize(true)
                        adapter = DetailParentAdapter(uiState.detailParentList, imageLoader)
                    }
                    FLIPPER_CHILD_POSITION_DETAIL
                }
                is UiActionStateLiveData.UiState.Error -> {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.categories.load(detailViewArgs.characterId)
                    }
                    FLIPPER_CHILD_POSITION_ERROR
                }
                UiActionStateLiveData.UiState.Empty -> {
                    FLIPPER_CHILD_POSITION_EMPTY
                }
            }
        }
    }

    private fun setObserveFavoriteUiState(detailViewArgs: DetailViewArgs) {

        viewModel.favorite.run {
            checkFavorite(detailViewArgs.characterId)
            binding.imageFavoriteIcon.setOnClickListener {
                viewModel.favorite.update(detailViewArgs)
            }

            state.observe(viewLifecycleOwner) { uiState ->
                binding.flipperFavorite.displayedChild = when (uiState) {
                    FavoriteUiActionStateLivedata.UiState.Loading -> FLIPPER_FAVORITE_CHILD_POSITION_LOADING
                    is FavoriteUiActionStateLivedata.UiState.Icon -> {
                        binding.imageFavoriteIcon.setImageResource(uiState.icon)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }
                    is FavoriteUiActionStateLivedata.UiState.Error -> {
                        showLongToast(uiState.messageResId)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }
                }
            }
        }
    }

    // Define a animação da transição como "move"
    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move).apply {
                sharedElementEnterTransition = this
            }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_DETAIL = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
        private const val FLIPPER_CHILD_POSITION_EMPTY = 3
        private const val FLIPPER_FAVORITE_CHILD_POSITION_IMAGE = 0
        private const val FLIPPER_FAVORITE_CHILD_POSITION_LOADING = 1
    }

}