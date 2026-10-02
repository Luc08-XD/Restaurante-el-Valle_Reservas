# Sabores del Valle — APF2 (Spring Boot + Thymeleaf)

## Requisitos
- JDK 17 o superior
- VS Code con "Extension Pack for Java" y "Spring Boot Extension Pack" (VS Code las sugiere al abrir la carpeta)

## Ejecutar en VS Code
1. File > Open Folder... > selecciona `sabores-del-valle` (la carpeta que contiene `pom.xml`).
2. Espera a que la barra inferior termine de importar el proyecto Maven.
3. Copia tus imágenes en `src/main/resources/static/img/`.
4. Pulsa F5 (o "Run" sobre `SaboresDelValleApplication.java`).
5. Abre http://localhost:8080

## Rutas
- `/`                 vista principal (Thymeleaf)
- `POST /reservas`    registra una reserva
- `/api/platos`       JSON de la carta
- `/api/platos/{id}`  JSON de un plato
- `/api/reservas`     JSON de las reservas en memoria
