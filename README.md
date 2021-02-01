# detector-gen-mutante

version 1 de detector gen mutante

# pre-requisitos

* Se debe tener instalado java version 11
* Se debe tener instalado gradle
* Tener Cuenta en Amazon AWS con los siguientes servicios
    1. Lambda function creada(donde se va a ejecutar la aplicacion)
    2. API gateway creado con los recursos necesarios para llamar a la lambda 

# generacion de Zip

Esta aplicacion esta construida en Java y para su despliegue primero se debe generar un zip con sus compilados

El comando para ejecutarlo es

 gradle clean build

generara un zip detector-gen-mutante-0.0.1-SNAPSHOT.zip en la ruta del proyecto /build/distrtibutions/

# despliegue

En la cuenta de Amazon AWS se debe adjuntar el zip generado en el paso anterior

en la "Configuración del tiempo de ejecución" de la lambda en el controlador se debe editar y 
colocar "detector.gen.mutante.aws.function.StartProcessRequestHandler" 

* [endpoint definido en el apigateway]/mutant 
* POST

**Ejemplo**

```

{
    "dna":["GTCGAGTA","TCGAGTAG","CGATTAGT","GAGAGGTC","GAGAGGTC","AGAGTCGT","AGAGTCGT","AGAGTCGT"]
}

```

## Arquitectura

![imagen perdida] (https://ibb.co/cNYByRM)