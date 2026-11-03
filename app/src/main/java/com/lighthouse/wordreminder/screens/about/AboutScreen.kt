package com.lighthouse.wordreminder.screens.about

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Coffee
import androidx.compose.material.icons.rounded.LocalPizza
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.billingclient.api.ProductDetails
import com.lighthouse.wordreminder.R
import com.lighthouse.wordreminder.ui.theme.Background
import com.lighthouse.wordreminder.ui.theme.BackgroundDark
import com.lighthouse.wordreminder.ui.theme.Primary
import com.lighthouse.wordreminder.ui.theme.White
import com.lighthouse.wordreminder.ui.theme.White70
import com.lighthouse.wordreminder.utils.BillingManager
import com.lighthouse.wordreminder.utils.GlobalDialogState

@Composable
fun AboutScreen(
    navController: NavController,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var showSupportDialog by remember { mutableStateOf(false) }

    val billingManager = remember(context) {
        BillingManager(context) { productId ->
            val tierTitle = when (productId) {
                BillingManager.PRODUCT_COFFEE -> context.resources.getString(R.string.support_tier_coffee)
                BillingManager.PRODUCT_SNACK -> context.resources.getString(R.string.support_tier_snack)
                BillingManager.PRODUCT_LUNCH -> context.resources.getString(R.string.support_tier_lunch)
                else -> context.resources.getString(R.string.support_developer)
            }
            openGlobalDialog(
                GlobalDialogState(
                    messageID = R.string.support_thank_you,
                    icon = Icons.Rounded.VolunteerActivism,
                    iconTint = Primary,
                    title = tierTitle
                )
            )
        }
    }

    DisposableEffect(billingManager) {
        onDispose {
            billingManager.destroy()
        }
    }

    LaunchedEffect(Unit) {
        billingManager.queryProducts()
    }

    val products by billingManager.products.collectAsState()

    val packageInfo = remember(context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(context.packageName, 0)
            }
        } catch (_: Exception) {
            null
        }
    }

    val versionName = packageInfo?.versionName ?: "1.0"

    if (showSupportDialog) {
        SupportDeveloperDialog(
            products = products,
            onDismiss = { showSupportDialog = false },
            onTierSelected = { productId ->
                showSupportDialog = false
                if (activity != null) {
                    billingManager.launchBillingFlow(
                        activity = activity,
                        productId = productId,
                        onError = {
                            openGlobalDialog(
                                GlobalDialogState(
                                    messageID = R.string.billing_unavailable,
                                    error = true
                                )
                            )
                        }
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 20.dp)
            .padding(top = 28.dp, bottom = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(42.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.about),
                style = MaterialTheme.typography.headlineLarge,
                color = White
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        // App Icon Centered (0.5f width, without box/background)
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.logo_desc),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth(0.5f)
        )

        // Version text (directly following the logo with 0 space)
        Text(
            text = stringResource(R.string.version_format, versionName),
            style = MaterialTheme.typography.bodyMedium,
            color = White70,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        // Support Developer Button (matches app theme)
        SupportDeveloperButton(
            onClick = { showSupportDialog = true }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SupportDeveloperButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        color = BackgroundDark,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.5.dp, Primary.copy(alpha = 0.45f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.VolunteerActivism,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.support_developer),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                )
                Text(
                    text = stringResource(R.string.support_developer_subtitle),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = White70
                    )
                )
            }
        }
    }
}

@Composable
fun SupportDeveloperDialog(
    products: Map<String, ProductDetails>,
    onDismiss: () -> Unit,
    onTierSelected: (productId: String) -> Unit
) {
    val coffeeTitle = stringResource(R.string.support_tier_coffee)
    val snackTitle = stringResource(R.string.support_tier_snack)
    val lunchTitle = stringResource(R.string.support_tier_lunch)

    val coffeePrice = products[BillingManager.PRODUCT_COFFEE]
        ?.oneTimePurchaseOfferDetails?.formattedPrice ?: "$0.99"
    val snackPrice = products[BillingManager.PRODUCT_SNACK]
        ?.oneTimePurchaseOfferDetails?.formattedPrice ?: "$2.99"
    val lunchPrice = products[BillingManager.PRODUCT_LUNCH]
        ?.oneTimePurchaseOfferDetails?.formattedPrice ?: "$4.99"

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(24.dp),
        containerColor = BackgroundDark,
        icon = {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.VolunteerActivism,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.support_dialog_title),
                style = MaterialTheme.typography.titleLarge,
                color = White,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.support_dialog_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    color = White70,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                )

                SupportTierCard(
                    icon = Icons.Rounded.Coffee,
                    title = coffeeTitle,
                    price = coffeePrice,
                    onClick = { onTierSelected(BillingManager.PRODUCT_COFFEE) }
                )

                SupportTierCard(
                    icon = Icons.Rounded.LocalPizza,
                    title = snackTitle,
                    price = snackPrice,
                    onClick = { onTierSelected(BillingManager.PRODUCT_SNACK) }
                )

                SupportTierCard(
                    icon = Icons.Rounded.Restaurant,
                    title = lunchTitle,
                    price = lunchPrice,
                    onClick = { onTierSelected(BillingManager.PRODUCT_LUNCH) }
                )
            }
        },
        confirmButton = {},
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.button_cancel),
                    style = MaterialTheme.typography.labelLarge,
                    color = White70
                )
            }
        }
    )
}

@Composable
fun SupportTierCard(
    icon: ImageVector,
    title: String,
    price: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = White.copy(alpha = 0.08f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = White
                )
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Primary.copy(alpha = 0.35f)
            ) {
                Text(
                    text = price,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = White,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}
