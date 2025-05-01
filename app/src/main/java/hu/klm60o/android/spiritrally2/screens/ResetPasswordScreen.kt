package hu.klm60o.android.spiritrally2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.android.spiritrally2.LoginScreen
import hu.klm60o.android.spiritrally2.R
import hu.klm60o.android.spiritrally2.assets.ErrorIcon
import hu.klm60o.android.spiritrally2.components.MyAlertDialog
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme
import hu.klm60o.android.spiritrally2.useful.resetUserPassword
import hu.klm60o.android.spiritrally2.useful.showToast
import hu.klm60o.android.spiritrally2.useful.validateEmail

@Composable
fun ResetPasswordScreenComposable(navController: NavController) {
    val context = LocalContext.current
    var validEmail = true
    val openAlertDialog = remember { mutableStateOf(false) }

    Surface {
        val userEmail = remember {
            mutableStateOf("")
        }

        //Oszlop a UI elemekhez
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp)) {

            //Kép
            Image(painter = painterResource(id = R.drawable.spirit_rally_transparent),
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))

            //Üdvözlő üzenet
            Text(text = "Írd be az email címed!", fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center
            )

            //Email bemeneti mező
            OutlinedTextField(value = userEmail.value, onValueChange = {
                userEmail.value = it
                validEmail = validateEmail(userEmail.value)
            },
                isError = !validEmail,
                supportingText = {
                    if(!validEmail) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Érvénytelen Email",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if(!validEmail) {
                        Icon(ErrorIcon,"error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "email")
                },
                label = {
                    Text(text = "Email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            //Jelszó megváltoztatása gomb
            ElevatedButton(onClick = {
                if(validEmail) {
                    resetUserPassword(userEmail.value) { error ->
                        if (error == null) {
                            openAlertDialog.value = true
                        } else {
                            showToast(context, "Error: ${error.message}")
                        }
                    }
                }
            },
                //A bejelentkezés gomb csak akkor lesz kattintható, ha az email és a jelszó is megfeleleően meg lett adva
                enabled = userEmail.value.isNotEmpty(),
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 10.dp)) {
                Text(text = "Jelszó megváltoztatása",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            //A bejelentkezéshez navigáló szöveg
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)) {
                Text(text = "Rossz helyen vagy?",
                    modifier = Modifier
                        .padding(5.dp))
                Text(text = "Bejelentkezés",
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            navController.navigate(LoginScreen)
                        },
                    fontWeight = FontWeight.Bold
                )
            }
            when {
                openAlertDialog.value -> {
                    MyAlertDialog(
                        onDismissRequest = { openAlertDialog.value = false},
                        onConfirmation = {
                            openAlertDialog.value = false
                            navController.navigate(LoginScreen)
                        },
                        dialogTitle = "Jelszó változtatás",
                        dialogText = "Jelszó változtatási kérelem elküldve a megadott email címre",
                        icon = Icons.Default.Email
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordPreview() {
    SpiritRally2Theme {
        ResetPasswordScreenComposable(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordPreviewDark() {
    SpiritRally2Theme(darkTheme = true) {
        ResetPasswordScreenComposable(
            navController = rememberNavController()
        )
    }
}