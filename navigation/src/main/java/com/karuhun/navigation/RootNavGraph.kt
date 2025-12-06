
package com.karuhun.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.karuhun.feature.onboarding.presentation.navigation.OnBoarding
import com.karuhun.feature.onboarding.presentation.navigation.onboardingGraph

@Composable
fun OnboardingNavGraph(
    navController: NavHostController,
    onOnboardingComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = OnBoarding,
        modifier = modifier
    ) {
        onboardingGraph(onNavigateToHome = onOnboardingComplete)
    }
}
