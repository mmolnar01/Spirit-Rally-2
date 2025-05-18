package hu.klm60o.android.spiritrally2.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.klm60o.android.spiritrally2.AuthActivity
import hu.klm60o.android.spiritrally2.presentation.userdata.UserDataViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import hu.klm60o.android.spiritrally2.components.LoadingIndicator
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.domain.model.UserData
import hu.klm60o.android.spiritrally2.presentation.userdata.components.AddUserDataFloatingActionButton
import hu.klm60o.android.spiritrally2.presentation.userdata.components.EmptyUserDataContent
import hu.klm60o.android.spiritrally2.presentation.userdata.components.UserDataContent
import hu.klm60o.android.spiritrally2.useful.showToast

@Composable
fun ProfileScreenComposable(navController: NavController, viewModel: UserDataViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val userDataListResponse by viewModel.userDataListState.collectAsStateWithLifecycle()
    val addUserDataResponse by viewModel.addUserDataState.collectAsStateWithLifecycle()
    val editUserDataResponse by viewModel.editUserDataState.collectAsStateWithLifecycle()

    val currentUserData = remember { mutableStateOf<UserData>(UserData()) }

    Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController) },
        topBar = { MyTopAppBar() },
        floatingActionButton = { AddUserDataFloatingActionButton(onAddUserData = viewModel::addUserData) }
    ) { innerPadding ->
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            //UserData lekérdezése és listázása
            when(val userDataListResponse = userDataListResponse) {
                is Response.Idle -> {}
                is Response.Loading -> LoadingIndicator()
                is Response.Success -> userDataListResponse.data?.let { userDataList ->
                    if (userDataList.isEmpty()) {
                        EmptyUserDataContent(innerPadding = innerPadding)
                    } else {
                        //Ez nem túl jó
                        //De megkeressük a listában a jelenlegi felhasználó elemét
                        //Aztán átadjuk a UserDataContent-nek
                        currentUserData.value = userDataList.firstOrNull { it.id == Firebase.auth.currentUser?.uid }!!
                        UserDataContent(innerPadding = innerPadding, currentUserData = currentUserData.value)
                    }
                }
                is Response.Failure -> userDataListResponse.e?.message?.let { errorMessage ->
                    LaunchedEffect(errorMessage) {
                        showToast(context, errorMessage)
                    }
                }
            }

            //UserData létrehozása
            when(val addUserDataResponse = addUserDataResponse) {
                is Response.Idle -> {}
                is Response.Loading -> LoadingIndicator()
                is Response.Success -> LaunchedEffect(Unit) {
                    showToast(context, "Versenyző adatai hozzáadva")
                    viewModel.resetAddUserDataState()
                }
                is Response.Failure -> addUserDataResponse.e?.message?.let { errorMessage ->
                    LaunchedEffect(errorMessage) {
                        showToast(context, errorMessage)
                        viewModel.resetAddUserDataState()
                    }
                }
            }

            /*Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(20.dp)
            ) {
                Icon(
                    Icons.Filled.Person, contentDescription = "Profile Icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .size(75.dp))

                OutlinedTextField(value = Firebase.auth.currentUser?.email.toString(), onValueChange = {},
                    label = {
                        Text("Email cím")
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(value = Firebase.auth.currentUser?.displayName.toString(), onValueChange = {},
                    label = {
                        Text("Csapatnév")
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(value = "Teszt", onValueChange = {},
                    label = {
                        Text("Rajtszám")
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(value = "Teszt", onValueChange = {},
                    label = {
                        Text("Kategória")
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedButton(onClick = {
                    Firebase.auth.signOut()
                    context.startActivity(Intent(context, AuthActivity::class.java))
                    val activity = context as? Activity
                    activity?.finish()

                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                    Text(text = "Kijelentkezés",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            }*/
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme {
        ProfileScreenComposable(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreviewDark() {
    hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme(darkTheme = true) {
        ProfileScreenComposable(
            navController = rememberNavController()
        )
    }
}