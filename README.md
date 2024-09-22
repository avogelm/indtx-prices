# Indtx Prices

Este repositorio implementa un microservicio encargado de almacenar tarifas de precios de productos, cada una de ellas aplicable dentro de un rango de fechas.
Estas tarifas pueden ser consultadas mediante la API REST que el microservicio ofrece en la ruta `/api/prices`. 

Una vez levantado el microservicio, para consultar el precio de un producto se deberá ejecutar la siguiente petición HTTP:
```
GET localhost:8080/api/prices/{brandID}/{productID}?timestamp={dateTime}
```
En la que:
- **brandID** representa el identificador numérico de la marca.
- **productID** representa el identificador numérico del producto.
- **dateTime** representa el momento de consulta, siguiento el formato `yyyy-MM-dd'T'HH:mm:ss`

La respuesta JSON seguirá el siguiente formato:
```json
{
    "productId": {productID},
    "brandId": {brandID},
    "priceList": {priceListID},
    "startDate": {startDate},
    "endDate": {endDate},
    "price": {pvp}
}
```
En la que:
- **productID** representa el identificador numérico del producto.
- **brandID** representa el identificador numérico de la marca.
- **priceListID** representa el identificador numérico de la tarifa aplicable.
- **startDate** representa la fecha de inicio de validez de la tarifa, en formato `yyyy-MM-dd'T'HH:mm:ss`
- **startDate** representa la fecha de inicio de vencimiento de la tarifa, en formato `yyyy-MM-dd'T'HH:mm:ss`
- **pvp** representa el precio de venta al público que aplicar al producto

## Funcionamiento y Arquitectura
El microservicio ha sido diseñado siguiendo el patrón de [puertos y adaptadores (Arquitectura Hexagonal)](https://alistair.cockburn.us/hexagonal-architecture/).

### Estructura de clases
![image](https://github.com/avogelm/indtx-prices/blob/master/Model%20Diagram.png)



## Modo de uso
- Instalación de dependencias: `mvn install`
- Ejecución de Tests: `mvn test`
- Feed de datos iniciales: `src/main/resources/data.sql`