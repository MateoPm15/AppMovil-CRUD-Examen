# 📌 **Sistema de Gestión de Cursos con Ubicación - Android App**  

### 📱 **Descripción**  
Esta es una aplicación móvil desarrollada en **Kotlin** y utilizando **SQLite** como base de datos local. Permite a los usuarios gestionar cursos, agregar estudiantes a cada curso y almacenar la ubicación geográfica de cada curso mediante coordenadas (latitud y longitud). Además, ofrece una funcionalidad de mapa interactivo que muestra la ubicación exacta de cada curso.

---

## 🚀 **Características Principales**
✅ **Gestión de Cursos:**  
   - Agregar, editar y eliminar cursos.  
   - Cada curso tiene un nombre, descripción, duración y ubicación geográfica.  

✅ **Gestión de Estudiantes:**  
   - Añadir estudiantes a un curso específico.  
   - Editar y eliminar estudiantes.  

✅ **Ubicación Geográfica:**  
   - Guardar la ubicación del curso (latitud y longitud).  
   - Opción para obtener la ubicación actual con un botón.  
   - Visualización de la ubicación en Google Maps con un marcador.  

✅ **Mapa Interactivo:**  
   - Al seleccionar un curso, se puede ver su ubicación exacta en Google Maps.  
   - Posibilidad de hacer zoom y navegar en el mapa.  

---

## 📂 **Estructura del Proyecto**
```
/app
│── /src
│   ├── /main
│   │   ├── /java/com/example/examen_crud
│   │   │   ├── ActivityAgregarEditarCurso.kt  # Maneja la creación y edición de cursos
│   │   │   ├── ActivityAgregarEditarEstudiante.kt  # Maneja la creación y edición de estudiantes
│   │   │   ├── ActivityCursoList.kt  # Lista de cursos disponibles
│   │   │   ├── ActivityEstudianteList.kt  # Lista de estudiantes en un curso
│   │   │   ├── Mapa.kt  # Muestra la ubicación de los cursos en Google Maps
│   │   │   ├── Controlador.kt  # Controlador para la gestión de la base de datos SQLite
│   │   │   ├── SqliteHelper.kt  # Helper para la creación y actualización de la base de datos SQLite
│   │   │   ├── Curso.kt  # Modelo de datos para un curso
│   │   │   ├── Estudiante.kt  # Modelo de datos para un estudiante
│   │   ├── /res/layout
│   │   │   ├── activity_agregar_editar_curso.xml  # Diseño de la pantalla para agregar/editar cursos
│   │   │   ├── activity_agregar_editar_estudiante.xml  # Diseño de la pantalla para agregar/editar estudiantes
│   │   │   ├── activity_curso_list.xml  # Diseño de la pantalla de lista de cursos
│   │   │   ├── activity_estudiante_list.xml  # Diseño de la pantalla de lista de estudiantes
│   │   │   ├── activity_mapa.xml  # Diseño de la pantalla del mapa
│── AndroidManifest.xml  # Configuración de la aplicación y permisos
```

---

## 🛠 **Tecnologías Utilizadas**
- **Lenguaje:** Kotlin  
- **Base de Datos:** SQLite  
- **Interfaz Gráfica:** XML  
- **Google Maps API:** Para la visualización de ubicaciones  
- **FusedLocationProviderClient:** Para obtener la ubicación del dispositivo  

---

## 📌 **Instalación y Configuración**
### 1️⃣ **Clonar el Repositorio**
```bash
git clone https://github.com/MateoPm15/AppMovil-CRUD-Examen
cd AppMovil-CRUD-Examen
```

### 2️⃣ **Abrir el Proyecto en Android Studio**
- Abre **Android Studio** y selecciona "Open an Existing Project".
- Navega hasta la carpeta del proyecto y ábrelo.

### 3️⃣ **Habilitar Google Maps API**
- Debes configurar la API de Google Maps en `AndroidManifest.xml`:
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="TU_API_KEY_AQUI" />
```
- Reemplaza `"TU_API_KEY_AQUI"` con tu clave de API de Google Maps.

### 4️⃣ **Ejecutar la Aplicación**
- Conecta un dispositivo Android o usa un emulador.
- Presiona el botón `Run` en Android Studio.

---

## 📍 **Uso de la Aplicación**
1️⃣ **Agregar un curso:**  
   - Presiona `+` en la lista de cursos.  
   - Ingresa nombre, descripción, duración y selecciona ubicación.  
   - Guarda el curso.  

2️⃣ **Agregar estudiantes a un curso:**  
   - En la lista de cursos, selecciona `Ver estudiantes`.  
   - Agrega nuevos estudiantes desde la pantalla de estudiantes.  

3️⃣ **Ver ubicación en el mapa:**  
   - Desde la lista de cursos, selecciona `Ver ubicación`.  
   - Se abrirá Google Maps y mostrará la ubicación del curso.  

---

## 🔧 **Errores Comunes y Soluciones**
| Error | Solución |
|--------|----------|
| `FATAL EXCEPTION: android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: Curso.id` | No pases `id = 0` al crear un curso, SQLite generará el ID automáticamente. |
| `No se muestra la ubicación en el mapa` | Verifica que la API Key de Google Maps esté correctamente configurada. |
| `Cannot resolve symbol '@id/etUbicacionCurso'` | Revisa que el `EditText` de la ubicación esté declarado en el XML con el ID correcto. |
| `ActivityNotFoundException` | Verifica que la actividad está declarada en `AndroidManifest.xml`. |

---

## 📜 **Licencia**
Este proyecto está bajo la licencia **MIT**. Puedes usarlo y modificarlo libremente.  

---

## 📩 **Contacto**
Si tienes dudas o sugerencias, contáctame en:  
📧 **Correo:** mateo.pilco.dev@gmail.com
🐙 **GitHub:** https://github.com/MateoPm15

---

💡 **¡Gracias por usar esta aplicación! 🚀**
