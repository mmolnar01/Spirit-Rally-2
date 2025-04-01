package hu.klm60o.android.spiritrally2.screens

import android.content.Intent
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.klm60o.android.spiritrally2.MainActivity
import hu.klm60o.android.spiritrally2.assets.ErrorIcon
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme
import hu.klm60o.android.spiritrally2.useful.loginUSer
import hu.klm60o.android.spiritrally2.useful.showToast
import hu.klm60o.android.spiritrally2.useful.validateEmail
import hu.klm60o.android.spiritrally2.useful.validatePassword
import hu.klm60o.android.spiritrally2.R
import hu.klm60o.android.spiritrally2.RegisterScreen
import hu.klm60o.android.spiritrally2.useful.findActivity

@Composable
fun LoginScreenComposable(navController: NavController) {
    var validEmail = true
    var validPassword = true
    val navController = navController
    val context = LocalContext.current

    Surface {
        val userEmail = remember {
            mutableStateOf("")
        }

        val userPassword = remember {
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
            Text(text = "Üdvözöl a Spirit Rally!", fontSize = 25.sp,
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

            //Jelszó bemeneti mező
            OutlinedTextField(value = userPassword.value, onValueChange = {
                userPassword.value = it
                validPassword = validatePassword(userPassword.value)
            },
                isError = !validPassword,
                supportingText = {
                    if(!validPassword) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "A jelszó legyen min. 5 karakteres",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if(!validPassword) {
                        Icon(ErrorIcon,"error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "password")
                },
                label = {
                    Text(text = "Jelszó")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            //Bejelentkezés gomb
            ElevatedButton(onClick = {
                if(validEmail && validPassword) {
                    loginUSer(userEmail.value, userPassword.value) { error ->
                        if(error == null) {
                            val currentUser = Firebase.auth.currentUser
                            if(currentUser?.isEmailVerified == true) {
                                //Lekérdezzük a bejlentkezett felhasználó eredményeit
                                //viewModel.teamName = currentUser.displayName
                                //viewModel.getUserDataFromFirestore(currentUser, context)

                                //newsViewModel.getNews()

                                context.startActivity(Intent(context, MainActivity::class.java))
                                /*BackHandler {
                                    context.findActivity()?.finish()
                                }*/
                                /*navController.navigate(NewsScreen) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }*/
                            } else {
                                showToast(context, "Az Email nincs megerősítve")
                            }
                        } else {
                            showToast(context, "Sikertelen bejelentkezés")
                        }
                    }
                }
            },
                //A bejelentkezés gomb csak akkor lesz kattintható, ha az email és a jelszó is megfeleleően meg lett adva
                enabled = userEmail.value.isNotEmpty() && userPassword.value.isNotEmpty(),
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)) {
                Text(text = "Bejelentkezés",
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
                    .padding(10.dp)) {
                Text(text = "Még nem regisztráltál?",
                    modifier = Modifier
                        .padding(5.dp))
                Text(text = "Regisztráció",
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            navController.navigate(RegisterScreen)
                        },
                    fontWeight = FontWeight.Bold
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    SpiritRally2Theme {
        LoginScreenComposable(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreviewDark() {
    SpiritRally2Theme(darkTheme = true) {
        LoginScreenComposable(
            navController = rememberNavController()
        )
    }
}