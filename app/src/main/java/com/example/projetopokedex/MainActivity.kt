package com.example.projetopokedex

import com.example.projetopokedex.ui.components.MainScaffold
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetopokedex.data.local.AppDatabase
import com.example.projetopokedex.data.repository.UserRepository
import com.example.projetopokedex.data.local.UserLocalDataSource
import com.example.projetopokedex.data.network.RetrofitConfig
import com.example.projetopokedex.data.network.repository.PokemonRepository
import com.example.projetopokedex.data.qrcode.QrCodeGenerator
import com.example.projetopokedex.data.repository.CardsRepository
import com.example.projetopokedex.ui.cards.CardDetailDialog
import com.example.projetopokedex.ui.login.LoginScreen
import com.example.projetopokedex.ui.login.LoginViewModel
import com.example.projetopokedex.ui.navigation.PokedexScreen
import com.example.projetopokedex.ui.signup.SignUpScreen
import com.example.projetopokedex.ui.signup.SignUpViewModel
import com.example.projetopokedex.ui.theme.ProjetoPokedexTheme
import kotlinx.coroutines.delay
import com.example.projetopokedex.ui.home.HomeScreen
import com.example.projetopokedex.ui.navigation.HomeTab
import com.example.projetopokedex.ui.home.HomeViewModel
import com.example.projetopokedex.ui.cards.CardsScreen
import com.example.projetopokedex.ui.cards.CardsViewModel
import com.example.projetopokedex.ui.profile.ProfileScreen
import com.example.projetopokedex.ui.profile.ProfileViewModel
import com.example.projetopokedex.ui.profile.DeleteAccountDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoPokedexApp()
        }
    }
}

class LoginViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SignUpViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class HomeViewModelFactory(
    private val userRepository: UserRepository,
    private val cardsRepository: CardsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(userRepository, cardsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class CardsViewModelFactory(
    private val cardsRepository: CardsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardsViewModel(cardsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ProfileViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun ProjetoPokedexApp() {
    ProjetoPokedexTheme {
        val context = LocalContext.current
        val navController = rememberNavController()

        val userLocalDataSource = remember { UserLocalDataSource(context) }
        val userRepository = remember { UserRepository(userLocalDataSource) }

        // Room + network
        val db = remember { AppDatabase.getInstance(context) }
        val pokemonService = remember { RetrofitConfig.api }
        val pokemonRepository = remember { PokemonRepository(pokemonService) }
        val cardsRepository = remember {
            CardsRepository(
                userCardDao = db.userCardDao(),
                pokemonRepository = pokemonRepository,
                userRepository = userRepository
            )
        }

        // ViewModels
        val loginViewModel: LoginViewModel = viewModel(
            factory = LoginViewModelFactory(userRepository)
        )

        val signUpViewModel: SignUpViewModel = viewModel(
            factory = SignUpViewModelFactory(userRepository)
        )

        val homeViewModel: HomeViewModel = viewModel(
            factory = HomeViewModelFactory(userRepository, cardsRepository)
        )

        val cardsViewModel: CardsViewModel = viewModel(
            factory = CardsViewModelFactory(cardsRepository)
        )

        val profileViewModel: ProfileViewModel = viewModel(
            factory = ProfileViewModelFactory(userRepository)
        )

        val startDestination = PokedexScreen.Login.name

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {

            composable(PokedexScreen.Login.name) {
                val state by loginViewModel.uiState.collectAsState()

                LoginScreen(
                    uiState = state,
                    onEmailChange = loginViewModel::onEmailChange,
                    onPasswordChange = loginViewModel::onPasswordChange,
                    onLoginClick = {
                        loginViewModel.login()
                    },
                    onNavigateToSignUp = {
                        navController.navigate(PokedexScreen.SignUp.name)
                    }
                )

                LaunchedEffect(state.isLoggedIn) {
                    if (state.isLoggedIn) {
                        delay(1000)
                        loginViewModel.clearCredentials()
                        navController.navigate(PokedexScreen.Home.name) {
                            popUpTo(PokedexScreen.Login.name) { inclusive = true }
                        }
                    }
                }

                LaunchedEffect(state.error, state.successMessage) {
                    if (state.error != null || state.successMessage != null) {
                        delay(2500)
                        loginViewModel.clearMessages()
                    }
                }
            }

            composable(PokedexScreen.SignUp.name) {
                val state by signUpViewModel.uiState.collectAsState()

                LaunchedEffect(state.isRegistered) {
                    if (state.isRegistered) {
                        delay(1000)
                        navController.popBackStack()
                        signUpViewModel.onRegistrationConsumed()
                    }
                }

                SignUpScreen(
                    uiState = state,
                    onAvatarChange = signUpViewModel::onAvatarChange,
                    onNameChange = signUpViewModel::onNameChange,
                    onEmailChange = signUpViewModel::onEmailChange,
                    onPasswordChange = signUpViewModel::onPasswordChange,
                    onSignUpClick = { signUpViewModel.register() },
                    onBackToLogin = { navController.popBackStack() }
                )
            }

            composable(PokedexScreen.Home.name) {
                val homeState by homeViewModel.uiState.collectAsState()
                var selectedTab by remember { mutableStateOf(HomeTab.Home) }

                MainScaffold(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        selectedTab = tab
                        when (tab) {
                            HomeTab.Home -> { /* já está na Home */ }
                            HomeTab.Cards -> {
                                navController.navigate(PokedexScreen.Cards.name)
                            }
                            HomeTab.Qr -> {
                                // tela de scanner
                            }
                            HomeTab.Profile -> {
                                navController.navigate(PokedexScreen.Profile.name)
                            }
                        }
                    },
                    overlayContent = homeState.lastDrawCard?.let { cardUi ->
                        {
                            val qrBitmap = remember(homeState.qrContent) {
                                homeState.qrContent?.let { content ->
                                    QrCodeGenerator.generate(content)
                                }
                            }

                            CardDetailDialog(
                                card = com.example.projetopokedex.ui.cards.CollectionCardUi(
                                    id = cardUi.cardNumber,
                                    card = cardUi
                                ),
                                isShowingBack = false,
                                onToggleFace = {  },
                                onDismiss = { homeViewModel.clearLastDraw() },
                                qrBitmap = qrBitmap
                            )
                            if (homeState.alreadyHasCard) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Você já realizou o sorteio de hoje. Volte amanhã.",
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    },
                    onOverlayDismiss = {
                        homeViewModel.clearLastDraw()
                    }
                ) {
                    HomeScreen(
                        uiState = homeState,
                        onLogoutClick = {
                            homeViewModel.logout()
                            loginViewModel.clearMessages()
                            loginViewModel.clearCredentials()
                            navController.navigate(PokedexScreen.Login.name) {
                                popUpTo(PokedexScreen.Home.name) { inclusive = true }
                            }
                        },
                        onSortearClick = { homeViewModel.drawCard() }
                    )
                }
            }

            composable(PokedexScreen.Cards.name) {
                var selectedTab by remember { mutableStateOf(HomeTab.Cards) }
                val state by cardsViewModel.uiState.collectAsState()

                MainScaffold(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        selectedTab = tab
                        when (tab) {
                            HomeTab.Home -> {
                                navController.navigate(PokedexScreen.Home.name) {
                                    popUpTo(PokedexScreen.Home.name) { inclusive = false }
                                }
                            }
                            HomeTab.Cards -> { /* já está na Home */ }
                            HomeTab.Qr -> { /* tela de scanner */ }
                            HomeTab.Profile -> {
                                navController.navigate(PokedexScreen.Profile.name)
                            }
                        }
                    },
                    overlayContent = state.selectedCard?.let { selected ->
                        {
                            CardDetailDialog(
                                card = selected,
                                isShowingBack = state.isShowingBack,
                                onToggleFace = { cardsViewModel.onToggleFace() },
                                onDismiss = { cardsViewModel.onDismissDialog() }
                            )
                        }
                    },
                    onOverlayDismiss = {
                        cardsViewModel.onDismissDialog()
                    }
                ) {
                    CardsScreen(viewModel = cardsViewModel)
                }
            }

            composable(PokedexScreen.Profile.name) {
                val profileState by profileViewModel.uiState.collectAsState()
                var selectedTab by remember { mutableStateOf(HomeTab.Profile) }

                LaunchedEffect(Unit) {
                    profileViewModel.onDismissDeleteDialog()
                }

                MainScaffold(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        selectedTab = tab
                        when (tab) {
                            HomeTab.Home -> navController.navigate(PokedexScreen.Home.name)
                            HomeTab.Cards -> navController.navigate(PokedexScreen.Cards.name)
                            HomeTab.Qr -> { /* scanner */ }
                            HomeTab.Profile -> { /* já está na Profile */ }
                        }
                    },
                    overlayContent = if (profileState.showDeleteDialog) {
                        {
                            DeleteAccountDialog(
                                onConfirm = {
                                    profileViewModel.confirmDeleteAccount {
                                        navController.navigate(PokedexScreen.Login.name) {
                                            popUpTo(PokedexScreen.Home.name) { inclusive = true }
                                        }
                                    }
                                },
                                onDismiss = { profileViewModel.onDismissDeleteDialog() }
                            )
                        }
                    } else null,
                    onOverlayDismiss = {
                        profileViewModel.onDismissDeleteDialog()
                    }
                ) {
                    ProfileScreen(
                        uiState = profileState,
                        onAvatarChange = profileViewModel::onAvatarChange,
                        onNameChange = profileViewModel::onNameChange,
                        onPasswordChange = profileViewModel::onPasswordChange,
                        onToggleEdit = profileViewModel::onToggleEdit,
                        onSaveChanges = profileViewModel::onSaveChanges,
                        onCancelEdit = profileViewModel::cancelEdit,
                        onToggleThemeIcon = profileViewModel::onToggleThemeIcon,
                        onDeleteClick = profileViewModel::onDeleteClick,
                        onDismissDeleteDialog = profileViewModel::onDismissDeleteDialog,
                        onConfirmDelete = {
                            profileViewModel.confirmDeleteAccount {
                                navController.navigate(PokedexScreen.Login.name) {
                                    popUpTo(PokedexScreen.Home.name) { inclusive = true }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}