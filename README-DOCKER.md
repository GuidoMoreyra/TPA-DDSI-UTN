# Docker Setup

Este proyecto incluye configuración completa de Docker para facilitar el desarrollo y deployment.

## Requisitos Previos

- Docker
- Docker Compose

## Arquitectura

El proyecto se compone de dos contenedores:

1. **mysql**: Base de datos MySQL 8.0
2. **app**: Aplicación Java con Javalin

## Inicio Rápido

### Iniciar todo el stack

```bash
docker-compose up
```

Para ejecutar en segundo plano:

```bash
docker-compose up -d
```

### Ver logs

```bash
# Todos los servicios
docker-compose logs -f

# Solo la aplicación
docker-compose logs -f app

# Solo la base de datos
docker-compose logs -f mysql
```

### Detener los servicios

```bash
docker-compose down
```

### Detener y eliminar volúmenes (limpieza completa)

```bash
docker-compose down -v
```

## Acceso a la Aplicación

Una vez iniciados los servicios:

- **Aplicación Web**: http://localhost:9001
- **Login**: http://localhost:9001/login
- **MySQL**: localhost:3306

### Credenciales por Defecto

**Aplicación:**
- Usuario: `admin`
- Password: `admin123`

**MySQL:**
- Root Password: `password`
- Database: `tp-ddsi`

## Configuración

### Variables de Entorno

Puedes personalizar la configuración editando el archivo `docker-compose.yml` o creando un archivo `.env`:

```env
# Base de datos
MYSQL_ROOT_PASSWORD=password
MYSQL_DATABASE=tp-ddsi
MYSQL_USER=app_user
MYSQL_PASSWORD=app_password

# Aplicación
DB_HOST=mysql
DB_PORT=3306
DB_NAME=tp-ddsi
DB_USER=root
DB_PASSWORD=password
```

## Desarrollo

### Rebuild después de cambios en el código

```bash
docker-compose up --build
```

### Rebuild solo la aplicación

```bash
docker-compose build app
docker-compose up -d app
```

### Ejecutar comandos Maven dentro del contenedor

```bash
# Entrar al contenedor
docker-compose exec app sh

# O ejecutar comandos directamente
docker-compose exec app mvn test
```

## Troubleshooting

### La aplicación no puede conectarse a MySQL

Asegúrate de que el servicio MySQL esté saludable:

```bash
docker-compose ps
```

El servicio `mysql` debe mostrar `healthy` en el estado.

### Reset completo de la base de datos

```bash
docker-compose down -v
docker-compose up
```

Esto eliminará el volumen de MySQL y creará una base de datos limpia.

### Ver logs de errores

```bash
docker-compose logs --tail=100 app
```

## Producción

Para producción, considera:

1. Cambiar las credenciales por defecto
2. Usar volúmenes nombrados para persistencia
3. Configurar backups de la base de datos
4. Usar variables de entorno desde archivos secretos
5. Implementar healthchecks en la aplicación

## Estructura de Archivos Docker

- `Dockerfile`: Build multi-stage para la aplicación Java
- `docker-compose.yml`: Orquestación de servicios
- `.dockerignore`: Archivos excluidos del contexto de build
