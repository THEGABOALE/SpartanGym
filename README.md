# SpartanGym · Plataforma de gestión de gimnasios

> Proyecto en evolución. El repositorio conserva el nombre histórico **SpartanGym** mientras se define una identidad independiente para la futura plataforma SaaS multi-gimnasio.

Sistema de gestión con panel web para administración/recepción, aplicación móvil Android y API REST. El estado actual es una aplicación con soporte de sucursales; **no debe considerarse un SaaS multi-tenant aislado y listo para producción** hasta completar las mejoras de autorización, segregación y pruebas indicadas abajo.

## Estructura

| Carpeta | Tecnología | Propósito |
| --- | --- | --- |
| `SpartanGymAPI/` | Java, Spring Boot, Spring Security, PostgreSQL | API, autenticación, pagos, socios, inventario, reportes y QR |
| `SpartanGymWeb/` | React, Vite, Tailwind | Interfaz web administrativa y de recepción |
| `SpartanGymApp/` | Kotlin, Jetpack Compose | Aplicación Android |
| `spartan_gym_schema.sql` | PostgreSQL | Esquema de referencia |
| `docs/` | Markdown | Arquitectura, desarrollo, migración y auditoría |

### Estado de la modernización
- ✅ React existente; TypeScript preparado de manera incremental mediante `tsconfig.json`, tipos iniciales y coexistencia JS/TS.
- ✅ Soporte **opt-in** de Redis como almacenamiento compartido de sesiones QR web; por defecto permanece el almacenamiento en memoria.
- ✅ Pipeline CI propuesto con Maven, npm y Android.
- ⏳ Migración completa del frontend a TypeScript, tests web, multi-tenancy, refuerzo de autorizaciones y calidad visual verificable: pendientes.

## Iniciar en desarrollo

**Requisitos:** JDK 21, Node.js 22, PostgreSQL y Android Studio/SDK para la app.

1. Clonar: `git clone https://github.com/THEGABOALE/SpartanGym.git`
2. Crear BD PostgreSQL e importar `spartan_gym_schema.sql`; configurar variables `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` en el entorno.
3. API: `cd SpartanGymAPI && ./mvnw spring-boot:run` (Windows: `mvnw.cmd`).
4. Web: `cd SpartanGymWeb && npm ci && npm run dev`. Configurar `VITE_API_BASE_URL` si la API no está en `http://localhost:8080/api`.
5. Android: abrir `SpartanGymApp` con Android Studio; URL API en `RetrofitClient.kt`.

Para despliegues en producción es obligatorio configurar un `JWT_SECRET` robusto. **También se debe corregir y configurar `ATTENDANCE_QR_SECRET`**: el fallback fijo actual es un riesgo, registrado entre los pendientes de seguridad. Nunca usar los secretos ni las contraseñas de ejemplo en producción.

## Redis opcional

El almacenamiento de sesiones QR sigue siendo `memory` por defecto para no romper despliegues de una sola instancia. Para compartir sesiones entre réplicas, configurar:

```dotenv
APP_QR_SESSION_STORE=redis
SPRING_DATA_REDIS_URL=rediss://<usuario>:<contraseña>@<host>:<puerto>
```

Usar los valores reales proporcionados por tu proveedor de Redis, únicamente como variables seguras en hosting. No registrar credenciales en archivos versionados. Los registros definitivos de asistencia, las membresías, los pagos y las ventas siguen en PostgreSQL.

## Desarrollo, pruebas y documentación

- [Arquitectura y evolución SaaS](docs/ARCHITECTURE.md)
- [Configuración de desarrollo y CI](docs/DEVELOPMENT.md)
- [Auditoría estática de UI/UX](docs/UI_UX_AUDIT.md)
- [Migración progresiva a React + TypeScript](docs/TYPESCRIPT_MIGRATION.md)
- [Issues](https://github.com/THEGABOALE/SpartanGym/issues) — mejoras priorizadas, sin afirmar que estén implementadas

Antes de fusionar una rama, ejecutar `./mvnw verify` (API), `npm run lint && npm run build` (web) y `./gradlew testDebugUnitTest assembleDebug` (Android), además de comprobar los resultados de GitHub Actions. El CI recién añadido **todavía requiere su primera validación en GitHub**.

## Seguridad y contribuciones

Mantener PR pequeños y revisión por módulo. No subir claves, secretos, respaldos de BD, tokens, `.env`, dependencias descargadas ni cachés locales. La selección de sucursal enviada por cliente no constituye autorización; el backend debe validar permisos por organización, sucursal y recurso. Validar migraciones de BD antes de desplegar.

## Nota sobre diseño y marca

La UI actual conserva textos e imágenes de la marca histórica Spartan Gym. La evolución multiempresa deberá abstraer colores, logotipos, textos e información comercial por organización, preservando contraste, navegación accesible y responsive.
