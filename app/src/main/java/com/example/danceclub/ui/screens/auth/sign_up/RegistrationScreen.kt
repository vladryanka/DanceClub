package com.example.danceclub.ui.screens.auth.sign_up

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.danceclub.R
import com.example.danceclub.data.model.Person
import com.example.danceclub.ui.theme.DanceClubTheme
import com.example.danceclub.ui.utils.PreviewLightDark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// добавить проверку на номер телефона. если уже есть в бд - нельзя зарегистрироваться!

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel(),
    onNavigateToProfile: (Person) -> Unit,
    onNavigateUpToGreeting: () -> Unit,
) {

    var textStateName by remember { mutableStateOf(TextFieldValue()) }
    var textStateAge by remember { mutableStateOf(TextFieldValue()) }
    var textStatePhone by remember { mutableStateOf(TextFieldValue()) }
    var textStatePassword by remember { mutableStateOf(TextFieldValue()) }
    var textStateReenterPassword by remember { mutableStateOf(TextFieldValue()) }
    val snackbarHostState = remember { SnackbarHostState() }
    var passwordVisible by remember { mutableStateOf(false) }
    var reenteredPasswordVisible by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var reenteredPassword by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
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
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = stringResource(
                                R.string.back
                            ), modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onNavigateUpToGreeting()
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
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                value = textStateAge,
                onValueChange = {
                    textStateAge = it
                    age = it.text.trim()
                },
                label = {
                    Text(
                        stringResource(id = R.string.age),
                        color = Color.Black
                    )
                },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = textStatePhone,
                onValueChange = {
                    textStatePhone = it
                    phone = it.text.trim()
                },
                label = {
                    Text(
                        stringResource(id = R.string.phone),
                        color = Color.Black
                    )
                },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    capitalization = KeyboardCapitalization.None // Можно установить в None для ввода номера телефона
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
                    if (name == "" || password == "" || reenteredPassword == "") {
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
                                val containsPerson =
                                    if (registrationViewModel.findPerson(phone) != null) {
                                        true
                                    } else false

                                if (!containsPerson) {
                                    val nameList = name.split(" ").toMutableList()
                                    if (nameList.size == 2) {
                                        nameList.add("")
                                    }
                                    val result = registrationViewModel.savePerson(
                                        name = nameList[1],
                                        surname = nameList[0],
                                        patronimic = nameList[2],
                                        age = age.toInt(),
                                        phone = phone, password
                                    )
                                    Log.d("Doing", result.first.toString())
                                    if (result.first) {
                                        val personForNav = registrationViewModel.findPerson(phone)
                                        personForNav?.let {
                                            Log.d(
                                                "Doing",
                                                personForNav.toString()
                                            )
                                        }
                                        withContext(Dispatchers.Main) {
                                            personForNav?.let { onNavigateToProfile(personForNav) }
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    "Пользователь не найден",
                                                    withDismissAction = true,
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                result.second,
                                                withDismissAction = true,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                } else {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Такой аккаунт уже существует",
                                            withDismissAction = true,
                                            duration = SnackbarDuration.Short
                                        )
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

@PreviewLightDark
@Composable
fun RegistrationScreenPreview() {
    DanceClubTheme {
        Surface {
            RegistrationScreen(
                onNavigateToProfile = {},
                onNavigateUpToGreeting = {}
            )
        }
    }
}