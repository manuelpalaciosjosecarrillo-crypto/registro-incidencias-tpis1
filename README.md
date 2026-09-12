# RegistroIncidencias

**Descripción:**
RegistroIncidencias es una aplicación móvil intuitiva y rápida para la captura de reportes. A través de una interfaz limpia construida con Jetpack Compose, permite a los usuarios ingresar los detalles de un evento o problema y visualizar la confirmación del reporte de forma inmediata en la misma pantalla.

## Propósito
Aplicación Android diseñada para el registro y gestión de incidencias.

## Herramientas utilizadas
* Android Studio
* Kotlin
* Jetpack Compose
* Git y GitHub

## Estado actual
* **Funciona:**
  * Pantalla inicial interactiva para la captura de incidencias (título y descripción).
  * Manejo de estado básico con `remember` y `mutableStateOf`.
  * Retroalimentación visual dinámica en la interfaz al presionar el botón "Crear reporte", mostrando el título destacado en negrita y la descripción en texto normal dentro de una tarjeta.
  * Generación de marca de tiempo (fecha y hora exacta) generada automáticamente y mostrada en la parte inferior de la tarjeta del reporte.
  * Personalización de contraste y colores en campos de entrada para mejorar la legibilidad.
  * Optimizaciones de Experiencia de Usuario (UX): Limpieza automática de los campos de texto, ocultamiento del teclado virtual y remoción del cursor (foco) tras la preparación del reporte.
* **Falta implementar:** Validación avanzada de formularios, ciclo de vida y persistencia de datos (base de datos o API).

## Registro de avances
* **Semana 6:** Implementación de interfaz con estado reactivo, captura de texto en tiempo real, limpieza automática de campos y retroalimentación inmediata al usuario.
* **Semana 7:** Mejoras de Experiencia de Usuario (UX) y presentación visual. Se integró la gestión de enfoque (ocultamiento automático de teclado y cursor al guardar), mejora tipográfica en la tarjeta de datos (título en negrita y descripción normal) y captura de la fecha y hora exacta del sistema.

## Cómo abrir el proyecto
1. Clonar este repositorio localmente.
2. Abrir Android Studio y seleccionar "Open".
3. Navegar hasta la carpeta del proyecto y esperar a que Gradle sincronice.

## Autor
José Manuel Palacios Carrillo
Universidad Tecnológica de El Salvador