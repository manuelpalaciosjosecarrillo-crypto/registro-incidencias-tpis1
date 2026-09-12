package sv.edu.utec.etps1.registroincidencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sv.edu.utec.etps1.registroincidencias.ui.theme.RegistroIncidenciasTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroIncidenciasTheme {
                RegistroIncidenciasApp()
            }
        }
    }
}

@Composable
fun RegistroIncidenciasApp() {
    // Variables de estado para los inputs
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Variables de estado para mostrar el reporte
    var reporteRegistrado by remember { mutableStateOf(false) }
    var tituloGuardado by remember { mutableStateOf("") }
    var descripcionGuardada by remember { mutableStateOf("") }
    var fechaHoraGuardada by remember { mutableStateOf("") }

    // Controladores para ocultar el teclado y quitar el cursor
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título principal
        Text(
            text = "Registro de incidencia",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        // Instrucción breve
        Text(
            text = "Completa los datos básicos del reporte.",
            textAlign = TextAlign.Center
        )

        // Campo de Título
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.DarkGray
            )
        )

        // Campo de Descripción
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.DarkGray
            )
        )

        // Acción principal (Button)
        Button(
            onClick = {
                if (titulo.isNotBlank() || descripcion.isNotBlank()) {
                    // Guardar los datos actuales en los estados del reporte
                    tituloGuardado = titulo
                    descripcionGuardada = descripcion
                    reporteRegistrado = true

                    // Generar fecha y hora actual
                    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                    fechaHoraGuardada = sdf.format(Date())

                    // Limpiar los campos de texto
                    titulo = ""
                    descripcion = ""

                    // Ocultar el teclado y quitar el enfoque del campo de texto
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Crear reporte")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card para mostrar la retroalimentación
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (!reporteRegistrado) {
                    Text(
                        text = "Aún no hay una incidencia registrada",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = "Reporte preparado:",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Título en negrita
                    Text(
                        text = tituloGuardado,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Descripción con texto normal
                    Text(
                        text = descripcionGuardada,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Fecha y hora bajo la tarjeta
        if (reporteRegistrado) {
            Text(
                text = "Generado el: $fechaHoraGuardada",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroIncidenciasPreview() {
    RegistroIncidenciasTheme {
        RegistroIncidenciasApp()
    }
}