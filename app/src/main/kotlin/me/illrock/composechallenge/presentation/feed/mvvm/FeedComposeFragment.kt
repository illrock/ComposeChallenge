package me.illrock.composechallenge.presentation.feed.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import me.illrock.composechallenge.data.entity.feed.card.BaseCard
import me.illrock.composechallenge.data.entity.feed.card.ImageTitleDescriptionCard
import me.illrock.composechallenge.data.entity.feed.card.TextCard
import me.illrock.composechallenge.data.entity.feed.card.TitleDescriptionCard
import me.illrock.composechallenge.data.entity.feed.card.content.ImageContent
import me.illrock.composechallenge.data.entity.feed.card.content.TextContent
import me.illrock.composechallenge.presentation.ComposeConstants
import me.illrock.composechallenge.presentation.safeParseColor
import me.illrock.composechallenge.utils.toDp

@AndroidEntryPoint
class FeedComposeFragment : Fragment() {
    private val vm : FeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent { FeedScreen(vm) }
        if (savedInstanceState == null) vm.updateFeed(false)
    }

    @Composable
    private fun FeedScreen(vm: FeedViewModel) {
        // Should apply app theme over here, and then use colors from it everywhere
        val isLoading: Boolean by vm.isLoading.observeAsState(false)
        if (isLoading) LoadingProgress()
        else FeedContent(vm)

        val errorRes: Int? by vm.errorRes.observeAsState(null)
        errorRes?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    @Composable
    private fun FeedContent(vm: FeedViewModel) {
        val isRefreshing: Boolean by vm.isRefreshing.observeAsState(false)
        val cards: List<BaseCard> by vm.cards.observeAsState(listOf())
        SwipeRefresh(
            rememberSwipeRefreshState(isRefreshing),
            onRefresh = { vm.updateFeed(true) },
            Modifier.background(Color.White)
        ) {
            CardList(cards)
        }
    }
}

@Composable
fun LoadingProgress() = Box(Modifier.background(Color.White), Alignment.Center) {
    CircularProgressIndicator(
        Modifier.size(ComposeConstants.PROGRESS_BAR_SIZE.dp)
    )
}

@Composable
fun CardList(cards: List<BaseCard>) = LazyColumn {
    cards.forEach {
        when (it) {
            is TextCard -> item { TextCardItem(it) }
            is TitleDescriptionCard -> item {
                TitleDescriptionCardItem(it.card.title, it.card.description)
            }
            is ImageTitleDescriptionCard -> item { ImageTitleDescriptionCardItem(it) }
        }
    }
}

@Composable
fun TextCardItem(data: TextCard) = CardText(
    data.card,
    Modifier.padding(ComposeConstants.PADDING_STANDARD.dp)
)

@Composable
fun TitleDescriptionCardItem(
    titleData: TextContent,
    descriptionData: TextContent,
    modifier: Modifier = Modifier
) = Column(modifier = modifier) {
    CardText(
        titleData,
        Modifier.padding(
            start = ComposeConstants.PADDING_STANDARD.dp,
            end = ComposeConstants.PADDING_STANDARD.dp,
            top = ComposeConstants.PADDING_STANDARD.dp,
            bottom = 0.dp
        )
    )
    CardText(
        descriptionData,
        Modifier.padding(ComposeConstants.PADDING_STANDARD.dp)
    )
}

@Composable
fun ImageTitleDescriptionCardItem(data: ImageTitleDescriptionCard) = Box(
    Modifier.height(data.content.image.size.height.toDp().dp)
) {
    CardImage(data.content.image)
    TitleDescriptionCardItem(
        data.content.title,
        data.content.description,
        Modifier.align(Alignment.BottomStart)
    )
}

@Composable
fun CardText(data: TextContent, modifier: Modifier = Modifier) {
    val parsedColor = safeParseColor(data.attributes.textColor)
    val textColor = parsedColor?.let { Color(it) }
        ?: ComposeConstants.TEXT_DEFAULT_COLOR
    Text(
        text = AnnotatedString(data.value),
        color = textColor,
        fontSize = data.attributes.font.size.sp,
        textAlign = TextAlign.Start,
        modifier = modifier
    )
}

@Composable
fun CardImage(data: ImageContent, modifier: Modifier = Modifier) = Image(
    painter = rememberImagePainter(data.url),
    null,
    contentScale = ContentScale.Crop,
    modifier = modifier.fillMaxSize(1f)
)