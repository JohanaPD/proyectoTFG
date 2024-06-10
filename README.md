# Manual de Instalación de MeetPsych

El presente documento explica los requisitos necesarios para poder ejecutar correctamente la aplicación.

## Requisitos del Sistema

Antes de proceder con la ejecución del proyecto hay que asegurarse de cumplir con los siguientes requisitos en el sistema operativo:

### Dependencia ant

El proyecto requiere la dependencia ant en el momento de la carga.

### Sistema Operativo

El proyecto es compatible con sistemas operativos Windows y Linux.

### Java Development Kit (JDK)

Es necesario tener una versión igual o superior a la 17 para poder ejecutar el programa correctamente. Más tarde se explicará cómo configurar el JDK.

### Espacio en Disco

Se recomienda disponer de al menos 100 MB de espacio en disco para la instalación.

## Pasos de Instalación

Para instalar y ejecutar correctamente MeetPsych hay que seguir los siguientes pasos:

### Paso 1: Descarga del Proyecto

Descarga del archivo `.jar` del proyecto en los archivos del sistema.

### Paso 2: Configuración del Entorno

Para garantizar el correcto funcionamiento de la aplicación, previamente hay que asegurarse de que esté bien configurado el JDK en nuestro sistema. A continuación se explica cómo se haría en cada sistema operativo:

#### En Windows

1. Descarga desde el [sitio oficial de Java](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) la versión del JDK mencionada anteriormente. Si ya dispones de una versión superior, puedes omitir este paso y continuar con la versión que tienes.
2. Verifica la configuración de las variables del sistema:
   - Accede a través de `Windows + Pausa`.
   - Selecciona `Configuración avanzada del sistema`.
   - Luego, `Variables de entorno`.
   - Dentro de `Variables del sistema`, elige la variable `Path` y añade la ruta donde se ubica la carpeta `bin` de tu JDK, como se ilustra en la figura adjunta.
   - Propiedades del sistema. Editar las variables del sistema.

#### En Linux

1. Abrir un terminal (`Ctrl+Alt+T`) e introducir el siguiente comando:

### sh
# Comando para instalar JDK (puede variar según la distribución)
sudo apt-get install openjdk-17-jdk

### Cargar la aplicación

Puedes cargar la aplicación, tras seguir las indicaciones, dando al play del MainApplication.

### Instalación de Ant

1. si estás cargando el programa desde InteliJ, simplemente desde ir a File> projectStructure> y descarga la dependencia ant.



