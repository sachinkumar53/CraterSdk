package com.crater.android.feature.social.ui.screen.instagram.component

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.FancyCard
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.data.model.social.InstagramPost
import com.crater.android.feature.social.ui.common.CommentItem
import com.crater.android.feature.social.ui.common.VideoInformation
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun InstagramPostItem(
    item: InstagramPost
) {
    var expanded by remember { mutableStateOf(false) }
    FancyCard(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(300)
            )
            .clickable {
                expanded = !expanded
            },
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimensions.spacingSmall)
        ) {
            VisiblePart(
                item = item,
                expanded = expanded
            ) {
                expanded = !expanded
            }
            if (expanded) {
                HiddenPart(item = item)
            }
        }
    }
}


@Composable
private fun VisiblePart(
    modifier: Modifier = Modifier,
    item: InstagramPost,
    expanded: Boolean,
    onExpandChange: () -> Unit
) {
    val rotation by animateFloatAsState(targetValue = if (expanded) 180f else 0f)
    Row(modifier = modifier.fillMaxWidth()) {
        GlideImage(
            imageModel = item.thumbnailUrl,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .size(96.dp)
                .aspectRatio(1f),
            shimmerParams = ShimmerParams(
                baseColor = AppTheme.colors.divider.copy(alpha = 0.6f),
                highlightColor = AppTheme.colors.divider
            ),
            contentScale = ContentScale.Crop
        )
        /*Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .size(96.dp)
                .background(MaterialTheme.colors.dividerColor)
                .aspectRatio(1f),
        )*/
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = AppTheme.dimensions.spacingSmall)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.publishedAt,
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                VideoInformation(
                    iconPainter = painterResource(id = R.drawable.ic_heart),
                    value = item.likeCount,
                    modifier = Modifier.weight(1f)
                )
                VideoInformation(
                    iconPainter = painterResource(id = R.drawable.ic_comments),
                    value = item.commentCount,
                    modifier = Modifier.weight(1f)
                )
                VideoInformation(
                    iconPainter = painterResource(id = R.drawable.ic_send),
                    value = item.shareCount,
                    modifier = Modifier.weight(1f)
                )

                VideoInformation(
                    iconPainter = painterResource(id = R.drawable.ic_save),
                    value = item.saveCount,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(rotation)
                .size(40.dp)
                .aspectRatio(1f),
            onClick = onExpandChange
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_down_arrow_white),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun HiddenPart(
    item: InstagramPost
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(vertical = AppTheme.dimensions.spacingExtraSmall)
    ) {

        if (item.comment != null) {
            CommentItem(item.comment)
        }

        OpenOnInstagram(
            onClick = {
                openInstagram(
                    context = context,
                    videoUrl = item.url
                )
            }
        )

    }
}


@Composable
private fun OpenOnInstagram(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = AppTheme.dimensions.spacingMedium)
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .height(52.dp)
            .background(Color(0x1FB1D1C4))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Open on ")
        Icon(
            painter = painterResource(id = R.drawable.ic_instagram_small),
            contentDescription = null
        )
        Text(
            text = " Instagram",
            fontWeight = FontWeight.Bold
        )
    }
}

@Stable
private fun openInstagram(
    context: Context,
    videoUrl: String
) {
    try {
        Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also {
            context.startActivity(it)
        }
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}
