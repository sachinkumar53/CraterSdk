package com.crater.android.feature.expense.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crater.android.R
import com.crater.android.feature.expense.domain.model.TransactionDetail
import com.crater.android.feature.expense.domain.model.TransactionType
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ExpenseRow(
    modifier: Modifier = Modifier,
    transaction: TransactionDetail
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .heightIn(56.dp)
            .background(
                color = Color(0xFF243931),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.primary)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = transaction.category.iconImageVector,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Inside
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = transaction.merchantName,
                fontSize = 14.sp,
                color = colorResource(id = R.color.white_text),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "${transaction.category} • ${transaction.date}",
                color = colorResource(id = R.color.gray_text),
                fontSize = 12.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = when (transaction.transactionType) {
                    TransactionType.Credit -> "+ ${transaction.amount}"
                    TransactionType.Debit -> "- ${transaction.amount}"
                    TransactionType.Unknown -> transaction.amount
                },
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (transaction.transactionType == TransactionType.Credit) colorResource(id = R.color.primary)
                else colorResource(id = R.color.white_text)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    imageModel = transaction.bank,
                    modifier = Modifier
                        .size(13.dp)
                        .aspectRatio(1f),
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = when {
                        transaction.accountNumber != null -> transaction.accountNumber
                        transaction.cardNumber != null -> transaction.cardNumber
                        else -> "----"
                    },
                    fontSize = 13.sp,
                    color = colorResource(id = R.color.white_text)
                )
            }

        }
    }
}