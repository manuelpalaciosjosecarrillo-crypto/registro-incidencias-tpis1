# RegistroIncidencias

**Descripción:**
RegistroIncidencias es una aplicación móvil intuitiva y rápida para la captura de reportes desarrollada en Android con Jetpack Compose. Permite a los usuarios ingresar los detalles de una incidencia, clasificar su prioridad y visualizar diagnósticos en tiempo real del estado del dispositivo a través de sus sensores integrados.

---

## Propósito
Aplicación Android diseñada para el registro y gestión de incidencias, integrando la lectura de múltiples sensores de hardware (acelerómetro y luz ambiental) para enriquecer el contexto ambiental y cinemático de los reportes, respaldada por un control estricto del ciclo de vida de la actividad.

---

## Herramientas utilizadas
- Android Studio
- Kotlin
- Jetpack Compose
- Git y GitHub

---

## Estado actual

### Funciona:
- **Pantalla inicial interactiva y adaptativa:** Formulario de captura (título y descripción) con soporte de desplazamiento vertical (`verticalScroll`) ante rotaciones de pantalla.
- **Persistencia de estado:** Protección del estado del formulario frente a cambios de configuración utilizando `rememberSaveable`.
- **Teclado contextual e IME:** Configuración de `KeyboardOptions` con `capitalization = KeyboardCapitalization.Sentences` y acciones IME (`ImeAction.Next` e `ImeAction.Done`) para navegación secuencial fluida y ocultamiento automático del teclado.
- **Interacción táctil de prioridad:** Selector dinámico de prioridad (*Baja*, *Media*, *Alta*) mediante `Card`s clicables con retroalimentación visual inmediata.
- **Diagnóstico multisensor en tiempo real:** Lectura de acelerómetro (`Sensor.TYPE_ACCELEROMETER`) y sensor de luz ambiental (`Sensor.TYPE_LIGHT`) integrados con `SensorManager`.
- **Diseño de interpretación textual:** Conversión automática de lecturas numéricas a métricas cualitativas (*"Dispositivo estable / En reposo"*, *"Movimiento detectado"*, *"Oscuridad total"*, *"Ambiente tenue / poca luz"*, *"Ambiente con mucha luz"*).
- **Gestión estricta del ciclo de vida:** Control de suscripción y liberación de los listeners mediante `DisposableEffect` y `onDispose` para evitar fugas de memoria y drenaje de batería.
- **Instrumentación y trazabilidad de la Activity:** Monitoreo completo de los 6 callbacks del ciclo de vida (`onCreate`, `onStart`, `onResume`, `onPause`, `onStop`, `onDestroy`) con la etiqueta `TPIS1_LIFECYCLE` registrado y probado en Logcat sobre un dispositivo físico Nokia G20.
- **Tarjeta de reporte:** Confirmación visual instantánea tras la creación de la incidencia con estampa temporal (fecha y hora exacta).

### Falta implementar:
- Validación avanzada de campos obligatorios.
- Lógica de "Shake" para accesos directos.
- Persistencia permanente en base de datos local (Room) o API remota.

---

## Registro de avances
- **Semana 6:** Implementación de la interfaz con estado reactivo, captura de texto en tiempo real, limpieza automática de campos y retroalimentación inmediata.
- **Semana 7:** Mejoras de UX y presentación visual. Gestión de enfoque (ocultamiento automático de teclado y cursor), jerarquía tipográfica y generación de fecha/hora exacta del sistema.
- **Semana 10:** Implementación de teclado contextual e interacción táctil. Integración de `KeyboardOptions` e `ImeAction` para navegación entre campos y selector de prioridad (*Baja*, *Media*, *Alta*) con `Modifier.clickable`.
- **Semana 11:** Integración técnica inicial del acelerómetro (`Sensor.TYPE_ACCELEROMETER`) mediante `SensorManager` y `DisposableEffect` para la gestión del ciclo de vida y visualización reactiva de los ejes X, Y, Z.
- **Semana 12:** Integración del sensor de luz ambiental (`Sensor.TYPE_LIGHT`), implementación del *Diseño de Interpretación* (traducción de métricas a frases descriptivas cualitativas sin números crudos), optimización total de ciclo de vida (`onDispose`), perfeccionamiento de UX por teclado virtual y documentación de evidencias en dispositivo físico.
- **Semana 14:** Instrumentación completa de los callbacks del ciclo de vida en `MainActivity` usando la etiqueta `TPIS1_LIFECYCLE`. Validación experimental en dispositivo físico (Nokia G20) registrando en Logcat 4 escenarios reales: apertura inicial, minimizado/retorno (Home), recreación por rotación de pantalla y cierre por botón Atrás / eliminación en aplicaciones recientes.
- **Semana 15:** Evaluación experimental de la conservación de estado de UI ante la recreación de la `Activity` por cambio de configuración (rotación de pantalla). Comparación práctica entre `remember` (pérdida de datos por estado volátil en composición) y `rememberSaveable` (persistencia a través del `SavedInstanceState` de Android).

---

## Detalles Técnicos de la Semana 12 (Sensores)

### 1. Integración del Sensor de Luz Ambiental y Acelerómetro
- **Acceso a Hardware:** Consulta e inicialización dual del acelerómetro (`Sensor.TYPE_ACCELEROMETER`) y sensor de luz (`Sensor.TYPE_LIGHT`) usando la API `SensorManager`.
- **Diseño de Interpretación (Sin números crudos):** Los valores brutos de los sensores se procesan mediante lógica condicional (`when`) para ofrecer retroalimentación cualitativa comprensible:
  - **Acelerómetro:** Evalúa la magnitud vectorial respecto a la gravedad ($9.81 m/s^2$) mostrando *"Dispositivo estable / En reposo"* o *"Movimiento detectado / Actividad cinemática"*.
  - **Sensor de Luz:** Categoriza las lecturas en luxes en frases como *"Oscuridad total"*, *"Ambiente tenue / poca luz"*, *"Iluminación normal de oficina"* y *"Ambiente con mucha luz / alta intensidad"*.

### 2. Control del Ciclo de Vida (`DisposableEffect` y `onDispose`)
- **Gestión de Recursos de Hardware:** Cada sensor cuenta con su respectivo bloque `DisposableEffect`, asegurando el registro de los listeners al entrar la vista en la composición y garantizando su desregistro explícito (`sensorManager.unregisterListener()`) dentro del método `onDispose()` cuando la pantalla se destruye o el composable sale del árbol visual, previniendo fugas de memoria y optimizando la batería.

### 3. Navegación Fluida con Teclado Virtual (UX)
- **Flujo de Entrada Continuo:** Uso combinado de `focusManager.moveFocus(FocusDirection.Down)` con `ImeAction.Next` en el campo *Título* para avanzar directamente a *Descripción*, y de `ImeAction.Done` con `focusManager.clearFocus()` y `keyboardController?.hide()` para ocultar el teclado al finalizar la redacción.

---

## Detalles Técnicos de la Semana 14 (Ciclo de Vida de MainActivity)

### 1. Instrumentación con Logcat
- **Monitoreo de Callbacks:** Sobrescritura e integración de logs de depuración mediante `Log.d(tag, msg)` en los 6 eventos clave de `MainActivity`:
  - `onCreate()`: Inicialización de la actividad y la UI en Compose.
  - `onStart()`: La actividad se vuelve visible en pantalla.
  - `onResume()`: La actividad adquiere el foco y queda lista para la interacción del usuario.
  - `onPause()`: La actividad pierde el foco de entrada.
  - `onStop()`: La actividad deja de ser visible.
  - `onDestroy()`: La instancia de la actividad se destruye completamente.
- **Filtro Personalizado:** Uso del tag unificado `TPIS1_LIFECYCLE` en Logcat para aislar y auditar las transiciones de estado sin interferencia de logs del sistema.

### 2. Verificación Experimental en Dispositivo Físico (Nokia G20)
- **Escenario 1 (Apertura inicial):** Secuencia limpia `onCreate` $\rightarrow$ `onStart` $\rightarrow$ `onResume`.
- **Escenario 2 (Ir a Home y reingresar):** Secuencia `onPause` $\rightarrow$ `onStop` al minimizar; `onStart` $\rightarrow$ `onResume` al reabrir sin requerir una nueva instanciación.
- **Escenario 3 (Rotación de pantalla):** Cambio de configuración que destruye y recrea la actividad: `onPause` $\rightarrow$ `onStop` $\rightarrow$ `onDestroy` $\rightarrow$ `onCreate` $\rightarrow$ `onStart` $\rightarrow$ `onResume`. Preservación del estado del formulario validada con `rememberSaveable`.
- **Escenario 4 (Cierre definitivo):** Uso de botón Atrás y deslizamiento en multitarea registrando `onPause` $\rightarrow$ `onStop` $\rightarrow$ `PROCESS ENDED`, confirmando la liberación total de la memoria RAM del sistema.

---

## Detalles Técnicos de la Semana 15 (Conservación de Estado ante Recreación)

### 1. Comparativa Experimental: `remember` vs `rememberSaveable`
- **Comportamiento con `remember`:** El estado de los campos del formulario (`titulo`, `descripcion`, `prioridad`) se almacena únicamente en la memoria de la composición activa. Al rotar el dispositivo, la destrucción y recreación de la `Activity` reinicia la composición, provocando la pérdida total de la información ingresada.
- **Comportamiento con `rememberSaveable`:** Al declarar las variables con `rememberSaveable`, el estado se guarda y recupera automáticamente a través del mecanismo `SavedInstanceState` (mediante un `Bundle` gestionado por Android), garantizando que los datos ingresados en `OutlinedTextField` y en la selección de prioridad se preserven de forma continua ante cambios de configuración.

---

## Cómo abrir el proyecto
1. Clonar este repositorio localmente.
2. Abrir Android Studio y seleccionar "Open".
3. Seleccionar la carpeta del proyecto y esperar a que Gradle sincronice los archivos.

---

## Autor
**José Manuel Palacios Carrillo**  
Universidad Tecnológica de El Salvador