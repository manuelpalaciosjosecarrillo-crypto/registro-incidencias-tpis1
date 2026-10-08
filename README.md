# RegistroIncidencias

**Descripción:**
RegistroIncidencias es una aplicación móvil intuitiva y rápida para la captura de reportes. A través de una interfaz limpia construida con Jetpack Compose, permite a los usuarios ingresar los detalles de un evento o problema y visualizar la confirmación del reporte de forma inmediata en la misma pantalla.

## Propósito
Aplicación Android diseñada para el registro y gestión de incidencias, integrando lectura de sensores del dispositivo para enriquecer el contexto ambiental de los reportes.

## Herramientas utilizadas
* Android Studio
* Kotlin
* Jetpack Compose
* Git y GitHub

## Estado actual
* **Funciona:**
  * Pantalla inicial interactiva para la captura de incidencias (título y descripción) con soporte de desplazamiento vertical (`verticalScroll`) ante cambios de orientación (rotación de pantalla).
  * Persistencia de estado robusta frente a cambios de configuración utilizando `rememberSaveable`.
  * **Teclado contextual e IME:** Configuración de `KeyboardOptions` con `capitalization = KeyboardCapitalization.Sentences`, además de acciones IME (`ImeAction.Next` e `ImeAction.Done`) para pasar automáticamente entre campos y ocultar el teclado.
  * **Interacción táctil de prioridad:** Selección dinámica de prioridad (*Baja*, *Media*, *Alta*) mediante componentes `Card` clicables (`Modifier.clickable`) con retroalimentación visual inmediata (resaltado de borde y color de contenedor).
  * **Lectura de Sensor (Acelerómetro):** Integración de `SensorManager` y `DisposableEffect` para capturar y mostrar en tiempo real los valores de aceleración en los ejes X, Y, Z, con manejo de disponibilidad del hardware.
  * Retroalimentación visual dinámica en la interfaz al presionar el botón "Crear reporte", mostrando el título en negrita, la prioridad seleccionada y la descripción dentro de una tarjeta.
  * Generación de marca de tiempo (fecha y hora exacta) generada automáticamente y mostrada en la parte inferior de la tarjeta del reporte.
  * Personalización de contraste y colores en campos de entrada para mejorar la legibilidad.
  * Optimizaciones de Experiencia de Usuario (UX): Limpieza automática de los campos de texto, ocultamiento del teclado virtual y remoción del cursor (foco) tras la preparación del reporte.
* **Falta implementar:** Validación avanzada de formularios, lógica de "Shake" para atajos y persistencia avanzada en base de datos local o API.

## Registro de avances
* **Semana 6:** Implementación de interfaz con estado reactivo, captura de texto en tiempo real, limpieza automática de campos y retroalimentación inmediata al usuario.
* **Semana 7:** Mejoras de Experiencia de Usuario (UX) y presentación visual. Se integró la gestión de enfoque (ocultamiento automático de teclado y cursor al guardar), mejora tipográfica en la tarjeta de datos (título en negrita y descripción normal) y captura de la fecha y hora exacta del sistema.
* **Semana 10:** Implementación de teclado contextual e interacción táctil. Integración de `KeyboardOptions` e `ImeAction` para navegación fluida de campos, y diseño de un selector táctil de prioridad (*Baja*, *Media*, *Alta*) utilizando `Modifier.clickable` con recomposición dinámica de interfaz.
* **Semana 11:** Integración técnica inicial de sensores del dispositivo. Implementación del acelerómetro (`Sensor.TYPE_ACCELEROMETER`) mediante `SensorManager`, `SensorEventListener` y `DisposableEffect` para la gestión segura del ciclo de vida del sensor, mostrando lecturas en tiempo real (ejes X, Y, Z) con validación de disponibilidad y soporte de scroll para visualización adaptativa en modo horizontal.

## Detalles Técnicos de la Semana 10

### 1. Entrada de Texto Contextual (`KeyboardOptions` y `KeyboardActions`)
* **Capitalización Automática:** Configuración de `KeyboardCapitalization.Sentences` para inicializar mayúsculas de manera automática en el inicio de frases.
* **Flujo de Foco Secuencial:** El campo *Título* hace uso de `ImeAction.Next` junto con `focusManager.moveFocus(FocusDirection.Down)` para transferir el foco al campo *Descripción* desde la acción del teclado virtual.
* **Cierre Programático:** El campo *Descripción* utiliza `ImeAction.Done` con `keyboardController?.hide()` y `focusManager.clearFocus()` para descartar el Soft Keyboard y limpiar la resalta del cursor en la UI.

### 2. Selector Táctil de Prioridad (`Modifier.clickable`)
* **Acciones Táctiles de Alto Nivel:** Se incorporó un componente selector compuesto por `Card`s individuales para definir la prioridad de la incidencia (*Baja*, *Media*, *Alta*).
* **Recomposición por Mutación de Estado:** El evento de toque activa la mutación del estado `prioridad`. Esto dispara una recomposición puntual en Jetpack Compose, actualizando dinámicamente las propiedades visuales `border` y `containerColor` del componente seleccionado.

## Detalles Técnicos de la Semana 11 (Sensores)

### 1. Integración del Acelerómetro (`SensorManager` y `DisposableEffect`)
* **Acceso al Hardware:** Se obtuvo una instancia del servicio del sistema `SensorManager` para consultar la disponibilidad del acelerómetro del dispositivo (`Sensor.TYPE_ACCELEROMETER`).
* **Gestión Segura del Ciclo de Vida:** Uso de `DisposableEffect` para registrar el `SensorEventListener` únicamente cuando el componente está activo y desregistrarlo de forma automática (`onDispose`) evitando fugas de memoria o consumo innecesario de batería.
* **Retroalimentación en Tiempo Real:** Actualización reactiva de las variables de estado para los ejes X, Y y Z, mostradas numéricamente en una tarjeta dedicada con control de disponibilidad (`sensorAvailable`).

## Cómo abrir el proyecto
1. Clonar este repositorio localmente.
2. Abrir Android Studio y seleccionar "Open".
3. Navegar hasta la carpeta del proyecto y esperar a que Gradle synchronice.

## Autor
José Manuel Palacios Carrillo  
Universidad Tecnológica de El Salvador