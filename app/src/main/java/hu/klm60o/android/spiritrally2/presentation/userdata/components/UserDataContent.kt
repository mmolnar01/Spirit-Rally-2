package hu.klm60o.android.spiritrally2.presentation.userdata.components

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.klm60o.android.spiritrally2.AuthActivity
import hu.klm60o.android.spiritrally2.domain.model.UserData

@Composable
fun UserDataContent(
    innerPadding: PaddingValues,
    currentUserData: UserData,
    checked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(5.dp)
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

        OutlinedTextField(value = currentUserData.number.toString(), onValueChange = {},
            label = {
                Text("Rajtszám")
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        OutlinedTextField(value = currentUserData.category.toString(), onValueChange = {},
            label = {
                Text("Kategória")
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(5.dp),
                text = "Helyzet folyamatos mentése")
            Switch(
                checked = checked,
                onCheckedChange = onChecked,
                thumbContent = {
                    if (checked) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                }
            )
        }

        OutlinedButton(onClick = {
            Firebase.auth.signOut()
            context.startActivity(Intent(context, AuthActivity::class.java))
            val activity = context as? Activity
            activity?.finish()
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(75.dp, 10.dp, 75.dp)) {
            Text(text = "Kijelentkezés",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        }
    }
}