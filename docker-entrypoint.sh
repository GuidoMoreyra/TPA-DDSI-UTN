#!/bin/sh
set -e

# Directorio de recursos dentro del JAR desempaquetado
RESOURCES_DIR="/tmp/app-resources"

echo "Iniciando aplicación..."
echo "Configuración de base de datos:"
echo "  Host: ${DB_HOST}"
echo "  Puerto: ${DB_PORT}"
echo "  Base de datos: ${DB_NAME}"
echo "  Usuario: ${DB_USER}"

# Crear directorio temporal
mkdir -p "$RESOURCES_DIR"
cd "$RESOURCES_DIR"

# Desempaquetar el JAR usando unzip
echo "Desempaquetando JAR..."
unzip -q /app/server-jar-with-dependencies.jar

# Verificar si existe el template, si no, usar el persistence.xml original
if [ -f "META-INF/persistence.xml.template" ]; then
    echo "Usando persistence.xml.template"
    TEMPLATE_FILE="META-INF/persistence.xml.template"
else
    echo "persistence.xml.template no encontrado, usando persistence.xml como template"
    cp META-INF/persistence.xml META-INF/persistence.xml.template
    TEMPLATE_FILE="META-INF/persistence.xml.template"
fi

# Reemplazar variables de entorno en persistence.xml
envsubst < "$TEMPLATE_FILE" > META-INF/persistence.xml

echo "persistence.xml generado con éxito"
cat META-INF/persistence.xml | grep "hibernate.connection"

# Ejecutar la aplicación desde el directorio desempaquetado
echo "Iniciando servidor..."
exec java -cp "$RESOURCES_DIR/*:$RESOURCES_DIR" ar.edu.utn.frba.dds.Main
