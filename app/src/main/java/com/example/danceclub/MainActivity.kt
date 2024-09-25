package com.example.danceclub

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danceclub.ui.theme.DanceClubTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(application)
        )[MainViewModel::class.java]
        setContent {
            DanceClubTheme {
                navController = rememberNavController()
                NavHost(navController = navController, startDestination = "greetingScreen"){
                    composable("greetingScreen") {
                        GreetingScreen(navController = navController)
                    }
                    composable("registrationScreen") {
                        RegistrationScreen(navController = navController, viewModel) //вызов вью из вьюмодели

                    }
                    composable("authorizationScreen") {
                        AuthorizationScreen(navController, viewModel)//вызов вью из вьюмодели
                    }

                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingScreen(navController: NavHostController) { // экран добро пожаловать
    Scaffold(
        topBar = @Composable {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(R.string.greeting), color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.dance_image),
                contentDescription = stringResource(
                    id = R.string.app_name
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("authorizationScreen") }) {
                Text(
                    text = stringResource(id = R.string.enter), color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("registrationScreen") }) {
                Text(
                    text = stringResource(id = R.string.registration), color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavHostController, viewModel: MainViewModel
) {

    var textStateUsername by remember { mutableStateOf(TextFieldValue()) }
    var textStateName by remember { mutableStateOf(TextFieldValue()) }
    var textStatePassword by remember { mutableStateOf(TextFieldValue()) }
    var textStateReenterPassword by remember { mutableStateOf(TextFieldValue()) }
    val snackbarHostState = remember { SnackbarHostState() }
    var passwordVisible by remember { mutableStateOf(false) }
    var reenteredPasswordVisible by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var reenteredPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = @Composable {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.backnew_image),
                            contentDescription = stringResource(
                                R.string.back
                            ), modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    navController.navigate("greetingScreen")
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(R.string.registration),
                            color = Color.Black,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(fontSize = 24.sp)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 80.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = textStateUsername,
                onValueChange = {
                    textStateUsername = it
                    username = it.text.trim()
                },
                label = {
                    Text(
                        stringResource(id = R.string.usernameID),
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = textStateName,
                onValueChange = {
                    textStateName = it
                    name = it.text.trim()
                },
                label = {
                    Text(
                        stringResource(id = R.string.username),
                        color = Color.Black
                    )
                },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = textStatePassword,
                onValueChange = {
                    textStatePassword = it
                    password = it.text.trim()
                },
                label = {
                    Text(
                        stringResource(id = R.string.password),
                        color = Color.Black
                    )
                },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions.Default
                    .copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            imageVector = image,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }

            )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = textStateReenterPassword,
                onValueChange = {
                    textStateReenterPassword = it
                    reenteredPassword = it.text.trim()
                },
                label = {
                    Text(
                        stringResource(id = R.string.reenter_the_password),
                        color = Color.Black
                    )
                },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions.Default
                    .copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (reenteredPasswordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (reenteredPasswordVisible)
                        Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = {
                        reenteredPasswordVisible = !reenteredPasswordVisible
                    }) {
                        Icon(
                            imageVector = image,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }

            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if (username == "" || name == ""
                        || password == "" || reenteredPassword == ""
                    ) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Заполните все поля",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else {
                        if (reenteredPassword != password)
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Пароли не совпадают",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        else {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (viewModel.findAccount(username)!=null){  // вызов вьюмодели во вью!
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Такой аккаунт уже существует",
                                            withDismissAction = true,
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                                else {
                                    viewModel.saveAccount( // вызов вьюмодели во вью!
                                        Account(
                                            username, password, name
                                        )
                                    )
                                    withContext(Dispatchers.Main){
                                        navController.navigate("greetingScreen") {
                                            popUpTo("greetingScreen") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    stringResource(id = R.string.save),
                    color = Color.Black, textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 24.sp)
                )
            }
        }
    }

}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    var textStateUsername by remember { mutableStateOf(TextFieldValue()) }
    var textStatePassword by remember { mutableStateOf(TextFieldValue()) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    var passwordVisible by remember { mutableStateOf(false) }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = @Composable {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.backnew_image),
                            contentDescription = stringResource(
                                R.string.back
                            ), modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    navController.navigate("greetingScreen")
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(R.string.authorization),
                            color = Color.Black,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(fontSize = 24.sp)
                        )
                    }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 80.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = textStateUsername,
                onValueChange = {
                    textStateUsername = it
                    username = it.text.trim()
                },
                label = {
                    Text(
                        stringResource(id = R.string.enter_the_username),
                        color = Color.Black
                    )
                },
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = textStatePassword,
                onValueChange = {
                    textStatePassword = it
                    password = it.text.trim()
                },
                label = {
                    Text(
                        stringResource(id = R.string.enter_the_password),
                        color = Color.Black
                    )
                },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions.Default
                    .copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            imageVector = image,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val account = viewModel.findAccount(username) // вызов вьюмодели во вью!

                        if (account == null) {
                            withContext(Dispatchers.Main) {
                                snackbarHostState.showSnackbar(
                                    "Введен некорректный username",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            if (account.password != password) {
                                withContext(Dispatchers.Main) {
                                    snackbarHostState.showSnackbar(
                                        "Введен некорректный пароль",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else {

                                        navController.navigate("sectionScreen") {
                                            Log.d("Doing", "sectionScreen")
                                            popUpTo("sectionScreen") {
                                                inclusive = true
                                            }
                                        }
                                    }
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    stringResource(id = R.string.enter), color = Color.Black,
                    textAlign = TextAlign.Center, style = TextStyle(fontSize = 24.sp)
                )
            }

        }
    }

}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DanceClubTheme {
        Greeting("Android")
    }
}