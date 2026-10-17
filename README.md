# RegistroIncidencias

**Descripción:**
RegistroIncidencias es una aplicación móvil intuitiva y rápida para la captura de reportes desarrollada en Android con Jetpack Compose. Permite a los usuarios ingresar los detalles de una incidencia, clasificar su prioridad y visualizar diagnósticos en tiempo real del estado del dispositivo a través de sus sensores integrados.

## Propósito
Aplicación Android diseñada para el registro y gestión de incidencias, integrando la lectura de múltiples sensores de hardware (acelerómetro y luz ambiental) para enriquecer el contexto ambiental y cinemático de los reportes.

## Herramientas utilizadas
- Android Studio
- Kotlin
- Jetpack Compose
- Git y GitHub

## Estado actual
- **Funciona:**
  - **Pantalla inicial interactiva y adaptativa:** Formulario de captura (título y descripción) con soporte de desplazamiento vertical (`verticalScroll`) ante rotaciones de pantalla.
  - **Persistencia de estado:** Protección del estado frente a cambios de configuración utilizando `rememberSaveable`.
  - **Teclado contextual e IME:** Configuración de `KeyboardOptions` con `capitalization = KeyboardCapitalization.Sentences` y acciones IME (`ImeAction.Next` e `ImeAction.Done`) para navegación secuencial fluida y ocultamiento automático del teclado.
  - **Interacción táctil de prioridad:** Selector dinámico de prioridad (*Baja*, *Media*, *Alta*) mediante `Card`s clicables con retroalimentación visual inmediata.
  - **Diagnóstico multisensor en tiempo real:** Lectura de acelerómetro (`Sensor.TYPE_ACCELEROMETER`) y sensor de luz ambiental (`Sensor.TYPE_LIGHT`) integrados con `SensorManager`.
  - **Diseño de interpretación textual (sin números crudos):** Conversión automática de lecturas numéricas a métricas cualitativas (*"Dispositivo stable / En reposo"*, *"Movimiento detectado"*, *"Oscuridad total"*, *"Ambiente tenue / poca luz"*, *"Ambiente con mucha luz"*).
  - **Gestión estricta del ciclo de vida:** Control de subscripción y liberación de los listeners mediante `DisposableEffect` y `onDispose` para evitar fugas de memoria y drenaje de batería.
  - **Tarjeta de reporte:** Confirmación visual instantánea tras la creación de la incidencia con estampa temporal (fecha y hora exacta).
- **Falta implementar:** Validación avanzada de campos obligatorios, lógica de "Shake" para accesos directos y persistencia persistente en base de datos local (Room) o API remota.

---

## Registro de avances
- **Semana 6:** Implementación de la interfaz con estado reactivo, captura de texto en tiempo real, limpieza automática de campos y retroalimentación inmediata.
- **Semana 7:** Mejoras de UX y presentación visual. Gestión de enfoque (ocultamiento automático de teclado y cursor), jerarquía tipográfica y generación de fecha/hora exacta del sistema.
- **Semana 10:** Implementación de teclado contextual e interacción táctil. Integración de `KeyboardOptions` e `ImeAction` para navegación entre campos y selector de prioridad (*Baja*, *Media*, *Alta*) con `Modifier.clickable`.
- **Semana 11:** Integración técnica inicial del acelerómetro (`Sensor.TYPE_ACCELEROMETER`) mediante `SensorManager` y `DisposableEffect` para la gestión del ciclo de vida y visualización reactiva de los ejes X, Y, Z.
- **Semana 12:** Integración del sensor de luz ambiental (`Sensor.TYPE_LIGHT`), implementación del *Diseño de Interpretación* (traducción de métricas a frases descriptivas cualitativas sin números crudos), optimización total de ciclo de vida (`onDispose`), perfeccionamiento de UX por teclado virtual y documentación de evidencias en dispositivo físico.

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

## Cómo abrir el proyecto
1. Clonar este repositorio localmente.
2. Abrir Android Studio y seleccionar "Open".
3. Seleccionar la carpeta del proyecto y esperar a que Gradle sincronice los archivos.

## Autor
**José Manuel Palacios Carrillo**  
Universidad Tecnológica de El Salvador