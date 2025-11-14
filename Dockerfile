# Etapa de construcción
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar archivos de configuración de Maven primero (para aprovechar caché de Docker)
COPY pom.xml .
COPY suppressions.xml .

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar la aplicación (sin ejecutar tests para build más rápido)
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine

# Instalar herramientas necesarias (gettext para envsubst, unzip para desempaquetar JAR)
RUN apk add --no-cache gettext unzip

WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar /app/

# Copiar el script de inicio
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

# Exponer el puerto de la aplicación
EXPOSE 9001

# Variables de entorno para la base de datos (pueden ser sobrescritas)
ENV DB_HOST=mysql
ENV DB_PORT=3306
ENV DB_NAME=tp-ddsi
ENV DB_USER=root
ENV DB_PASSWORD=password

# Usar el script de inicio
ENTRYPOINT ["docker-entrypoint.sh"]
