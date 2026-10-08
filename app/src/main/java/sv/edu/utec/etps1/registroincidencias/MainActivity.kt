package sv.edu.utec.etps1.registroincidencias

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    // Variables de estado persistentes ante cambios de configuración (rotación de pantalla) con rememberSaveable
    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var prioridad by rememberSaveable { mutableStateOf("Media") }

    // Variables de estado para mostrar el reporte guardado
    var reporteRegistrado by rememberSaveable { mutableStateOf(false) }
    var tituloGuardado by rememberSaveable { mutableStateOf("") }
    var descripcionGuardada by rememberSaveable { mutableStateOf("") }
    var prioridadGuardada by rememberSaveable { mutableStateOf("") }
    var fechaHoraGuardada by rememberSaveable { mutableStateOf("") }

    // Controladores para ocultar el teclado y gestionar el foco
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val opcionesPrioridad = listOf("Baja", "Media", "Alta")

    // Estado del Scroll para permitir desplazamiento vertical al rotar la pantalla
    val scrollState = rememberScrollState()

    // --- INTEGRACIÓN DEL SENSOR (ACELERÓMETRO) ---
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    // Variables de estado para los datos del sensor
    var sensorDataX by remember { mutableStateOf(0f) }
    var sensorDataY by remember { mutableStateOf(0f) }
    var sensorDataZ by remember { mutableStateOf(0f) }
    val sensorAvailable = accelerometer != null

    DisposableEffect(accelerometer) {
        if (accelerometer != null) {
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    sensorDataX = event.values[0]
                    sensorDataY = event.values[1]
                    sensorDataZ = event.values[2]
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }
            sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

            onDispose {
                sensorManager.unregisterListener(listener)
            }
        } else {
            onDispose { }
        }
    }
    // --- FIN INTEGRACIÓN SENSOR ---

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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

        // 1. Campo de Título
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

        // 2. Campo de Descripción
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

        // 3. Interacción Táctil: Selector de Prioridad
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
                            .clickable { prioridad = opcion },
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
                    tituloGuardado = titulo
                    descripcionGuardada = descripcion
                    prioridadGuardada = prioridad
                    reporteRegistrado = true

                    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                    fechaHoraGuardada = sdf.format(Date())

                    titulo = ""
                    descripcion = ""

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

        Spacer(modifier = Modifier.height(8.dp))

        // --- TARJETA DE VISUALIZACIÓN DEL SENSOR ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Estado del Movimiento (Acelerómetro)",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))

                if (sensorAvailable) {
                    Text("X: ${String.format("%.2f", sensorDataX)} m/s²", style = MaterialTheme.typography.bodySmall)
                    Text("Y: ${String.format("%.2f", sensorDataY)} m/s²", style = MaterialTheme.typography.bodySmall)
                    Text("Z: ${String.format("%.2f", sensorDataZ)} m/s²", style = MaterialTheme.typography.bodySmall)
                } else {
                    Text(
                        text = "Acelerómetro no disponible en este dispositivo.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        // --- FIN TARJETA DE VISUALIZACIÓN ---
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroIncidenciasPreview() {
    RegistroIncidenciasTheme {
        RegistroIncidenciasApp()
    }
}