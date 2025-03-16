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
import hu.klm60o.android.spiritrally2.useful.showToast
import hu.klm60o.android.spiritrally2.useful.validateEmail
import hu.klm60o.android.spiritrally2.useful.validatePassword

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
                    viewModel.loginUSer(userEmail.value, userPassword.value) { error ->
                        if(error == null) {
                            val currentUser = Firebase.auth.currentUser
                            if(currentUser?.isEmailVerified == true) {
                                //Lekérdezzük a bejlentkezett felhasználó eredményeit
                                //viewModel.teamName = currentUser.displayName
                                viewModel.getUserDataFromFirestore(currentUser, context)

                                newsViewModel.getNews()

                                navController.navigate(NewsScreen) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                                /*val userDocumentReference = Firebase.firestore.collection("race_results").document(currentUser.uid)
                                    .collection("my_race_results").document("my_race_results_current")

                                userDocumentReference.get().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val document = task.result

                                        //Ha létezik a dokumentum, akkor beállítjuk a viewModel mezőit és megyünk a NewsScreen-re
                                        if (document.exists()) {
                                            val userRaceData = document.toObject(UserViewModel::class.java)
                                            viewModel.distance = userRaceData?.distance
                                            viewModel.racePoints = userRaceData?.racePoints
                                            viewModel.teamName = userRaceData?.teamName
                                            viewModel.achievedRacePoints = userRaceData!!.achievedRacePoints
                                            showToast(context, "Sikeres bejelentkezés!")
                                            navController.navigate(NewsScreen) {
                                                popUpTo(navController.graph.id) {
                                                    inclusive = true
                                                }
                                            }

                                            //Ha nem létezik a dokumentum, akkor letöltjük a verseny részleteit, és létrehozzuk a dokumentumot
                                        } else {
                                            var currentRaceData: CurrentRaceData?
                                            Firebase.firestore.collection("race_data").document("current_race_data_test").get()
                                                .addOnCompleteListener { task ->
                                                    if(task.isSuccessful) {
                                                        val raceDocument = task.result
                                                        //Ha sikerül és létezik, betöltjük a viewModel-be és megyünk a NewsScreen-re
                                                        if(raceDocument.exists()) {
                                                            currentRaceData = raceDocument.toObject(CurrentRaceData::class.java)

                                                            viewModel.distance = currentRaceData?.distance
                                                            val racePoints = mutableListOf<RacePoint>()

                                                            racePoints.add(RacePoint(1, currentRaceData?.start_point, null))

                                                            var i = 2
                                                            for (geoPoint in currentRaceData?.intermediate_points!!) {
                                                                racePoints.add(RacePoint(i, geoPoint, null))
                                                                i++
                                                            }

                                                            racePoints.add(RacePoint(i, currentRaceData?.end_point, null))

                                                            viewModel.racePoints = racePoints

                                                            viewModel.teamName = currentUser.displayName

                                                            userDocumentReference.set(viewModel).addOnCompleteListener { task ->
                                                                if (task.isSuccessful) {
                                                                    Log.d(ContentValues.TAG, "Created user document: Success")
                                                                    showToast(context, "A dokumentum sikeresen létrehozva")

                                                                    showToast(context, "Sikeres bejelentkezés!")
                                                                    navController.navigate(
                                                                        NewsScreen
                                                                    ) {
                                                                        popUpTo(navController.graph.id) {
                                                                            inclusive = true
                                                                        }
                                                                    }
                                                                } else {
                                                                    Log.d(ContentValues.TAG, "Created user document: Failure")
                                                                    showToast(context, "A dokumentum létrehozása sikertelen")
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                        }
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
    SpiritRallyTheme {
        hu.klm60o.android.spiritrally2.screens.LoginScreenComposable(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreviewDark() {
    hu.klm60o.spiritrally2.ui.theme.SpiritRallyTheme(darkTheme = true) {
        hu.klm60o.android.spiritrally2.screens.LoginScreenComposable(
            navController = rememberNavController()
        )
    }
}