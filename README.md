# GEOLOCALIZACIÓN DE IP'S
<hr/>

API desarrollada con SpringBoot `3.2.5` y `Java 21` :). Puedes aprovechar la interfaz gráfica de Swagger para realizar peticiones a la API.

## Docker | ¿Cómo correr la aplicación?
Para correr la aplicación, sólo necesitarás de dos comandos :)

<b>1. Obtener la imagen desde Docker Hub</b>

La imagen se encuentra en el repositorio público de DockerHub, la puedes ver aquí: ``https://hub.docker.com/repository/docker/juliandaviid/geolocalizacion-ip/general``

Como ya se encuentra en un repositorio público en la nube, ejecuta el siguiente comando:

``docker pull juliandaviid/geolocalizacion-ip``

Esto descargará la imagen de la aplicación en tus imágenes de docker, puedes verla con el siguiente comando:

``docker image ls``

Una vez tienes la imagen descargada, sólo queda correr el contenedor

<b>2. Correr el contenedor con la imagen obtenida de Docker Hub</b>

Ejecuta el siguiente comando y ¡listo!, tendrás la aplicación corriendo en el puerto ``8080`` tanto de tu máquina como dentro de Docker.

``docker container run -dp:8080:8080 juliandaviid/geolocalizacion-ip``

## Endpoint Swagger (Interfaz Gráfica)
Una vez tenemos nuestro contenedor corrriendo, para acceder a la interfaz gráfica de Swagger desde el navegador y realizar peticiones a la API, sólo tienes que ingresar a la siguiente ruta:

<b>Swagger Endpoint:</b>`http://localhost:8080/swagger-ui/index.html`

Una vez allí, verás cuatro peticiones:

## 1. Geolocalizar una API
<hr/>

Para localizar una `IP`, basta con realizar una petición a la URL: `http://localhost:8080/api/v1/geo/ip/localize?ip=83.44.196.93`.
Nota que existe un parámetro en la URL llamado ``ip``, este valor lo igualas a la IP que quieras consultar.

### Ejemplo JSON de salida:

````
{
    "ip": "83.44.196.93",
    "actualDate": "22/05/2024 00:54:25",
    "country": "Spain",
    "isoCode": "es",
    "languages": "Languages: Spanish (spa), Catalan (cat), Basque (eus), Galician (glc), ",
    "currency": "Currency: EUR (1 EUR = 0.9208600977 $ USD)",
    "countryDate": "Timezones: 00:54:25 (UTC), 01:54:25 (UTC+01:00), ",
    "estimateDistance": "10513 kms (41.9272, 2.2586) to (-34.6167, -58.368)"
}
````
## 2. Consultar distancia más larga
<hr />

Para obtener la distancia más larga desde dónde se han hecho peticiones tomando como punto de referencia ``Buenos Aires``, realizaremos una petición ``GET`` a la ruta ``/api/v1/geo/ip/larger-distance``.

### Ejemplo Salida

````
>>>La distancia más lejana es: 10513 km
````

Si no hay información, recibiremos lo siguiente:

````
No se encontró información:

{
    "code": "210",
    "message": "No hay información en base de datos",
    "issuedDate": "22/05/2024 01:26:38"
}
````
## 3. Consultar distancia más corta
<hr />

Para obtener la distancia más corta desde dónde se han hecho peticiones tomando como punto de referencia ``Buenos Aires``, realizaremos una petición ``GET`` a la ruta ``/api/v1/geo/ip/closest-distance``.

### Ejemplo Salida

````
>>>La distancia más cercana es: 10513 km
````

Si no hay información, recibiremos lo siguiente:

````
No se encontró información:

{
    "code": "210",
    "message": "No hay información en base de datos",
    "issuedDate": "22/05/2024 01:26:38"
}
````
## 4. Consultar promedio de distancias
<hr />

Para obtener el promedio de distancia basándonos en todas las peticiones realizadas y el número de veces que se invocó la consulta de una IP, llamaremos el endpoint ``GET`` ``/api/v1/geo/ip/average-distance``.
### Ejemplo Salida

````
>>>La distancia promedio es de: 10513 km
````

Si no hay información, recibiremos lo siguiente:

````
No se encontró información:

{
    "code": "210",
    "message": "No hay información en base de datos",
    "issuedDate": "22/05/2024 01:26:38"
}
````

## 5. Errores no controlados
<hr/>

Si dentro del flujo de ejecución de la aplicación, se lanza una excepción no controlada, recibiremos el siguiente mensaje personalizado:

````
{
    "code": "500",
    "message": {Descripción del error},
    "issuedDate": {Fecha actual}"
}
````

## API's utilizadas
<hr/>

Para el desarrollo de este microservicio, se consumieron las siguientes API's:

<b>1. Información de la IP:</b> http://ip-api.com/json/ (API gratuita).

<b>2. Información del país:</b> https://restcountries.com/v3.1/ (API gratuita).

<b>3. Información de las monedas:</b> https://api.freecurrencyapi.com/v1 (API gratuita).

## PERSISTENCIA
<hr/>
Dada una IP (Ej: 8.8.8.8), el microservicio sólo consultará las tres API's descritas anteriormente una vez, la siguiente petición que consulte esta misma IP, traerá la información de la base de datos H2 (base de datos en memoria). Esta base de datos es volatil, es decir, se crea al momento de la ejecución del servicio y se elimina en el momento que se detenga la aplicación.

## CONSIDERACIONES
<hr/>

1. Se utilizaron API's gratuitas ya que las propuestas en el ejercicio requerían un método de pago.
2. La API de información de monedas no tiene el catálogo completo (por ej: no está la moneda COP), en esos casos, el servicio responde en la propiedad currency con: ``"currency": "Currency: COP - Dollar information not available"``.
3. Es posible que las API's puedan presentar intermitencia al momento de hacer el consumo.
4. La función que calcula la distancia geográfica entre Buenos Aires y las coordenadas de la dirección IP no es de mi autoría.
5. Muchas gracias :)