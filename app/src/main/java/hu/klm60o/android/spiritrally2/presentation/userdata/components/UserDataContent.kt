package hu.klm60o.android.spiritrally2.presentation.userdata.components

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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

@Composable
fun UserDataContent(innerPadding: PaddingValues) {
    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.Center,
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
    }
}