package com.crater.android.feature.social.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.social.domain.model.Comment


@Composable
fun CommentItem(comment: Comment) {
    Column(modifier = Modifier.fillMaxWidth()) {
        FancyHeader(
            title = {
                Text(text = stringResource(id = R.string.comments))
            },
            /*trailing = {
                Text(text = stringResource(id = R.string.see_all))
            }*/
        )
        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingTiny))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${comment.userName} • ${comment.commentDate}",
                style = AppTheme.typography.labelLarge
            )
            Text(
                text = comment.text,
                style = AppTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = AppTheme.dimensions.spacingTiny)
            )
        }
    }
}


