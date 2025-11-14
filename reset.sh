#!/bin/bash

echo "🔄 Iniciando reinicio total de la aplicación..."
echo ""

# 1. Matar cualquier proceso Java que esté corriendo el proyecto
echo "1️⃣ Deteniendo procesos Java existentes..."
pkill -f "ar.edu.utn.frba.dds" 2>/dev/null || echo "   No hay procesos corriendo"
sleep 2

# 2. Limpiar completamente el directorio target
echo ""
echo "2️⃣ Limpiando directorio target/..."
mvn clean

# 3. Opcional: Resetear la base de datos MySQL
echo ""
echo "3️⃣ ¿Deseas resetear la base de datos MySQL? (s/n)"
read -r respuesta
if [[ "$respuesta" =~ ^[Ss]$ ]]; then
    echo "   Reseteando base de datos..."
    mysql -u root -ppassword -e "DROP DATABASE IF EXISTS \`tp-ddsi\`; CREATE DATABASE \`tp-ddsi\`;" 2>/dev/null
    if [ $? -eq 0 ]; then
        echo "   ✓ Base de datos reseteada"
    else
        echo "   ⚠ No se pudo resetear la BD (puede que no esté corriendo MySQL)"
    fi
else
    echo "   Omitiendo reseteo de BD"
fi

# 4. Recompilar completamente
echo ""
echo "4️⃣ Recompilando proyecto..."
mvn compile

# 5. Mostrar instrucciones finales
echo ""
echo "✅ Reinicio completo finalizado!"
echo ""
echo "Ahora puedes ejecutar la aplicación con:"
echo "  mvn exec:java"
echo ""
echo "O con tu configuración de IntelliJ/Eclipse"
echo ""
