# Auditoría UI/UX — 2026-10-15

Alcance: inspección ESTÁTICA de Home, Login, App, AdminLayout, RecepcionistaLayout, CSS, tarjetas y sucursales. No se realizó una evaluación visual en un navegador ni pruebas de usuario.

## Fortalezas
- Rutas y paneles diferenciados por rol; navegación móvil desplegable.
- Uso de React.lazy, feedback de carga, clases responsive.
- Tema claro/oscuro y variables de acento reutilizables.

## P0/P1
- **Legibilidad y contraste:** existen etiquetas `text-[9px]`, `text-[10px]`, `text-[11px]` en métricas y páginas principales. Establecer tamaños mínimos accesibles; validar WCAG 2.2 AA, zoom 200%, contraste y lectores de pantalla.
- **Identidad visual:** las clases `red-*`, colores hexadecimales y referencias a Spartan están repartidas. Definir tokens CSS por organización antes de white-label.
- **Login:** selección de rol manual; revisar si aporta valor frente a redirigir según rol autenticado. Asociar cada `label` a su input, identificar errores por campo y no mostrar solamente mensajes genéricos.
- **Estados de operación:** normalizar vacío/cargando/error/éxito en tablas, pagos y formularios.

## P2
- Componentes duplicados de layouts admin/recepción; extraer componentes accesibles reutilizables.
- Auditoría de navegación por teclado, foco visible, aria, overlays, escape y atrapado de foco en menús.
- Reducir motion para `prefers-reduced-motion`; comprobar timers/carruseles automáticos de Home.
- Revisar pantallas a 320, 375, 768, 1024 y 1440 px, modo claro/oscuro y Android.

## Criterios de aceptación
Capturas y pruebas por perfil, dispositivos, estados, navegación por teclado, pruebas axe y pruebas manuales con usuarios. No marcar auditoría visual como completada hasta disponer de capturas/evidencia de navegador.
