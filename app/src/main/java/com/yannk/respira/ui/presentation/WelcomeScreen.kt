package com.yannk.respira.ui.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yannk.respira.R
import com.yannk.respira.ui.components.FundoImg
import com.yannk.respira.ui.components.VectorImg


@Preview(showBackground = false)
@Composable
fun WelcomeScreenn() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fundo
        FundoImg()

        // Curva no fundo (vetor)
        VectorImg(
            modifier = Modifier.align(Alignment.BottomEnd),
            source = R.drawable.vector
        )

        //Caixa de Texto
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(15.dp)
                .align(Alignment.BottomEnd),
            verticalArrangement = Arrangement.Center
        ) {

            Text("Bem Vindo!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text("Cuide da sua respiração com segurança.",
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text("Monitore, registre e enfrente as crises com apoio.",
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))


            Button(onClick = {},
                modifier = Modifier
                    .width(120.dp)
                    .align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4E87F6)
                )

            ) {
                Text("Próximo",
                    fontWeight = FontWeight.Bold)

                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.keyboard_arrow_up),
                    contentDescription = "Arrow"
                )
            }
        }
    }
}

