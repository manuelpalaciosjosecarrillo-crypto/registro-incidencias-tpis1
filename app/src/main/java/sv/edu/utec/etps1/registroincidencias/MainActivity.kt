package sv.edu.utec.etps1.registroincidencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
    var prioridad by remember { mutableStateOf("Media") } // Interacción táctil: Prioridad seleccionada

    // Variables de estado para mostrar el reporte guardado
    var reporteRegistrado by remember { mutableStateOf(false) }
    var tituloGuardado by remember { mutableStateOf("") }
    var descripcionGuardada by remember { mutableStateOf("") }
    var prioridadGuardada by remember { mutableStateOf("") }
    var fechaHoraGuardada by remember { mutableStateOf("") }

    // Controladores para ocultar el teclado y gestionar el foco
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val opcionesPrioridad = listOf("Baja", "Media", "Alta")

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

        // 1. Campo de Título con KeyboardOptions e ImeAction
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.DarkGray
            )
        )

        // 2. Campo de Descripción con KeyboardOptions e ImeAction
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.DarkGray
            )
        )

        // 3. Interacción Táctil: Selector de Prioridad mediante Cards Clicables
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Selecciona la prioridad (Toca una opción):",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                opcionesPrioridad.forEach { opcion ->
                    val esSeleccionado = prioridad == opcion
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { prioridad = opcion }, // Interacción táctil de alto nivel
                        border = BorderStroke(
                            width = if (esSeleccionado) 2.dp else 1.dp,
                            color = if (esSeleccionado) MaterialTheme.colorScheme.primary else Color.Gray
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (esSeleccionado) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = opcion,
                            textAlign = TextAlign.Center,
                            fontWeight = if (esSeleccionado) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }

        // Acción principal (Button)
        Button(
            onClick = {
                if (titulo.isNotBlank() || descripcion.isNotBlank()) {
                    // Guardar los datos en el reporte
                    tituloGuardado = titulo
                    descripcionGuardada = descripcion
                    prioridadGuardada = prioridad
                    reporteRegistrado = true

                    // Generar fecha y hora
                    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                    fechaHoraGuardada = sdf.format(Date())

                    // Limpiar campos de texto
                    titulo = ""
                    descripcion = ""

                    // Ocultar teclado y quitar el enfoque
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Crear reporte")
        }

        Spacer(modifier = Modifier.height(8.dp))

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

                    Text(
                        text = tituloGuardado,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Prioridad: $prioridadGuardada",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))

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