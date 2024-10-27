# bricks
Bricks challenge api

# API de Gestión de Productos

Este proyecto es una API REST desarrollada en Java con Spring Boot con base de datos  en memoria H2, que permite gestionar productos de un comercio. Además, integra la obtención de categorías de productos desde un servicio externo.
Basado en una arquitectura hexagonal para facilitar la modularización, escalabilidad y facilitar el mantenimiento del api a largo plazo, entre otros beneficios como permitir cambiar elementos de infraestructura
o de entrada/salida sin afectar el núcleo de negocio.
Se eligio como arquitectura porque proporciona una base sólida para soportar cambios, integraciones y actualizaciones en el software, sin sacrificar calidad y manteniendo la claridad en la lógica del negocio.

## Funcionalidades

La API proporciona los siguientes endpoints para gestionar los productos:

- `GET /product`: Obtiene el listado de productos, con opciones de filtrado por `name`, `price`, `stock` y `category`. La respuesta está paginada.
- `GET /product/{id}`: Obtiene un producto específico por `id`.
- `POST /product`: Crea un nuevo producto.
- `DELETE /product`: Elimina un producto.
- `PUT /product`: Actualiza un producto.
- `GET /category`: Lista las categorías. Las categorías se obtienen a través de una integración con un servicio externo y aplicando cache.

## Cache

Se implemento Caffeine Cache para reducir los llamados al api externa siguiendo la premisa de que se permiten como maximo 10 llamados diarios.
Por lo siguiente puede acceder a las variables de tiempo en la ruta: **bricks.trading.config.CacheConfig**

Donde el campo CATEGORY_TTL tiene el valor de 144 (minutos) lo que asegura la limitacion a 10 llamadas en 24hs.
`CATEGORY_TTL = 144`

## Ejecucion y consulta de la BD

Por defecto la ejecucion del API es http://localhost:8080/
y el acceso al admin de la BD H2 en Memoria es http://localhost:8080/h2-console/
con USERNAME: "sa" PASS: "password".

## Estructura de la Entidad Product

La entidad `Product` incluye los siguientes campos:

- `id`: `int`, autoincremental, identificador único del producto.
- `name`: `String`, nombre del producto.
- `price`: `double`, precio del producto.
- `stock`: `int`, cantidad en stock.
- `category`: `String`, categoría a la que pertenece el producto.

## Servicios

### Endpoints para Productos

### 1. Obtener todos los productos

**Endpoint**: `GET /products`

**Descripción**: Devuelve una lista de todos los productos.

**Parámetros de consulta**:
- `page` (opcional, int, valor por defecto: 0): Número de la página.
- `size` (opcional, int, valor por defecto: 10): Tamaño de la página.

**Respuesta**:
- Código: 200 OK
- Cuerpo: `ApiResponseDto<List<ProductResponseDto>>` con los productos paginados.

**Ejemplo de llamada**:
```bash
curl -X GET "http://localhost:8080/products?page=0&size=10"
```
### 2. Obtener un producto por ID

**Endpoint**: `GET /products/{id}`

**Descripción**: Busca y devuelve un producto por su ID.

**Parámetros de consulta**:
- `id` (int, requerido): ID del producto a consultar.

**Respuesta**:
- Código: 200 OK si se encuentra el producto.
- Cuerpo: `ApiResponseDto<List<ProductResponseDto>>` con el producto encontrado.

**Ejemplo de llamada**:
```bash
curl -X GET "http://localhost:8080/products/1"
```
### 3. Crear un nuevo producto

**Endpoint**: `POST /products`

**Descripción**: Crea un nuevo producto en el sistema.

**Cuerpo de solicitud**:
- `ProductCreateDto` (JSON): Datos necesarios para crear un producto, incluyendo name, category, stock, y price.

**Respuesta**:
- Código: 201 Created.
- Cuerpo: `ApiResponseDto<List<ProductResponseDto>>` con el producto creado.

**Ejemplo de llamada**:
```bash
curl -X POST "http://localhost:8080/products" -H "Content-Type: application/json" -d '{
  "name": "Crucero Rio de Janeiro",
  "category": "VIA",
  "stock": 100,
  "price": 29.99
}'
```
### 4. Actualizar un producto por ID

**Endpoint**: `PUT /products/{id}`

**Descripción**: Actualiza los datos de un producto existente.

**Parametros**:
- `int` (int, requerido): ID del producto a actualizar.

**Cuerpo de solicitud**:
- `ProductCreateDto` (JSON): Datos necesarios para crear un producto, incluyendo name, category, stock, y price.

**Respuesta**:
- Código: 201 Created.
- Cuerpo: `ApiResponseDto<List<ProductResponseDto>>` con el producto actualizado.

**Ejemplo de llamada**:
```bash
curl -X PUT "http://localhost:8080/products/1" -H "Content-Type: application/json" -d '{
  "name": "Producto Actualizado",
  "category": "VIA",
  "stock": 150,
  "price": 34.99
}'
```
### 5. Eliminar un producto por ID

**Endpoint**: `DELETE /products/{id}`

**Descripción**: Elimina un producto del sistema según el ID especificado.

**Parametro de ruta**:
- `int` (int, requerido): ID del producto a eliminar.

**Respuesta**:
- Código: 204 No Content si la eliminación fue exitosa.

**Ejemplo de llamada**:
```bash
curl -X DELETE "http://localhost:8080/products/1"
```
### Endpoints para Categorías

### 1. Obtener todas las categorias

**Endpoint**: `GET /category`

**Descripción**: Devuelve una lista de todas las categorías.

**Respuesta**:
- Código: 200 OK
- Cuerpo: `ApiResponseDto<List<CategoryResponseDto>>` con las categorías.

**Ejemplo de llamada**:
```bash
curl -X GET "http://localhost:8080/category"
```
