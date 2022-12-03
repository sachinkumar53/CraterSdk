package com.crater.android.feature.profile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun ProfileHeader(
    name: String,
    phone: String,
    email: String,
    isVerified: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.surfaceVariant)
            .padding(
                vertical = AppTheme.dimensions.spacingLarge,
                horizontal = AppTheme.dimensions.spacingMedium
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_default_profile),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Spacer(Modifier.size(AppTheme.dimensions.spacingSmall))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = name, style = AppTheme.typography.titleLarge)

                if (isVerified)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check_verified),
                        contentDescription = null,
                        tint = AppTheme.colors.primary,
                        modifier = Modifier
                            .padding(start = AppTheme.dimensions.spacingExtraSmall)
                            .size(AppTheme.dimensions.iconSmall)
                    )
            }

            Spacer(Modifier.size(AppTheme.dimensions.spacingTiny))
            Text(text = "(+91) $phone", color = AppTheme.colors.textSecondary)
            Spacer(Modifier.size(2.dp))
            Text(text = email, color = AppTheme.colors.textSecondary)
        }
    }
}


@Preview
@Composable
fun ProfilePreview() {
    CraterTheme {
        ProfileHeader(
            name = "Sachin",
            phone = "7004153730",
            email = "kr771.sachin@gmail.com",
            isVerified = true
        )
    }
}