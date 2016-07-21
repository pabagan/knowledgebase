# Analisis de algoritmos

## Qué es
El tiempo de ejecución depende de la cantidad de elementos de la entrada. Además influye la velocidad de la máquina, calidad del compilador. Se estudian la eficiencia teniendo como base la misma máquina.

##### Ejemplo descarga de archivo

```bash
retraso inicial = '2sec'
velocidad de descarga = '1,6k/sec'
tamaño archivo = 'N kilobytes'

# descripcion del tiempo de descarga
T(N) = N/1,6 + 2 

# resultados
con fichero de 80k = 52 sec
con fichero de 160k = 102 sec

# observaciones
al dobar el tamaño de la entrasa vemos que el tiempo de descarga se ha doblado. Esto es propio de un algoritmo lineal (mejor escenario posible).

```

## Valores de entrada pequeños

Son dificiles de evaluar. Por ejemplo la curva cuadrática es mejor que la O(N log N). Para tamaños de entrada pequeños la diferencia en tiempos de ejecución es insignificante. No es necesario preocuparnos de ellos y la regla práctica es usar el algoritmo más sencillo.

## Valores de entrada grandes
Algoritmos cuadráticos y cúbicos serán impracticables a los pocos cientos o miles respectivamente.

## Funciones comunes de tiempo de ejecución

| Función       | Nombre                    |
| ------------- |:-------------------------:|
| c             | Constante                 |
| log N         | Logarítmica               |
| log N**2      | Logaritmica al cuadrado   |
| N             | Lineal                    |
| N log N       | N log N                   |
| N**2          | Cuadrática                |
| N**3          | Cúbica                    |
| 2**N          | Exponencial               |

## Ejemplos de tiempo de ejecución

### Elemento mínimo en un vector
**enunciado:** Dado N elemento encontrar el más pequeño.

Opción 1: inicializar variable min y recorrer el vector actualizando el valor de min. Sería de tipo lineal O(N) ya que representa una cantidad fija de trabajo por cada elemento del vector.


### Puntos más cercanos en el plano
**enunciado:** Dado N puntos(x,y) en un plano encontrar los planos que estén más cercanos.

Opción 1: calcular la distancia entre cada punto conservando la distancia mínima. Este algoritmo es O(N**2).

### Puntos colineales
**enunciado:** Dado N puntos(x,y) en un plano encontrar 3 en línea recta.

Opción 1: calcular la distancia entre cada punto conservando la distancia mínima. Este algoritmo es O(N**3).

## Problema de la subsecuencia de suma máxima
Data una secuencia de enteros (posiblemente negativos) `A1, A2, ..., An` encontrar (identificando la subsecuencia) el valor máximo y la subsecuencia correspondiente. Si todos son negativos la subsecuencia está vacia y siendo la suma 0.

**ejemplos 1:** {-2, **11, -4, 13,** -5, 2 } = 20
**ejemplos 2:** {1, -3, **4, -2, -1, 6** } = 7

Hay muchas algoritmos para solucionar y su eficiencia varía radicalmente.

### Algoritmo obvio cúbico O(N**3)

```java
public static int subsecuenciSumaMaxima(int [] a){
    int sumaMax = 0;
    
    for(int i = 0; i < a.length; i++) {
        for(int j = i; j < a.length; j++) {
            int sumaActual = 0;
            
            for(int k = i; k <= j; k ++)
                sumaActual += a[k];

            if( sumaActual > sumaMax ) {
                sumaMax = sumaActual;
                secIni = i;
                secFin = j;
            }
        }
    }

    return sumaMax;
}
```

El **tiempo de ejecución** del algorítmo está dominado por su bucle interno en el que 4 expresiones son las más repetidas:

1. inicialión `k = i`
2. comprobación `k <= j`
3. modificación de `sumaActual += a[k]`;
4. incremento `k++`

El resto de expresiones no tienen consecuencias porque se ejecutan una vez por iteración. El número de veces que se ejecute `sumaActual += a[k]` es una medida dominante del trabajo del bucle más interno. En este caso es de O(N3).

Observando en código vemos un bucle de tamaño potencial N, dentro de otro potencialmente de N, dentro de otro de tamaño potencial N. Esos bucles generan N * N * N (N**3).


Se adopta la **regla general** de multiplicar el coste del bucle más interno por el tamaño de cada bucle anidado. 

Se adopta la **regla general** tiempo de ejecución de las instrucciones multiplicado por el número de iteraciones. 



Si no hubiera bucles anidados tendríamos un comportamiento lineal.


### Algoritmo mejorado O(N**2)

```java
public static int subsecuenciSumaMaxima(int [] a){
    int sumaMax = 0;
    
    for(int i = 0; i < a.length; i++) {
        int sumaActual = 0;
        for(int j = i; j < a.length; j++) {
            sumaActual += a[j];
            
            if( sumaActual > sumaMax ) {
                sumaMax = sumaActual;
                secIni = i;
                secFin = j;
            }
        }
    }

    return sumaMax;
}
```


### Algoritmo lineal
Se denota `Ai,Aj, .., AN` como la subsecuencia que abarca de i a j, siendo `Si,j` su suma.

* si `Si,j < 0`. Si `q > j` entonces `Ai,q` no es la subsecuencia máxima.

```java
public static int subsecuenciSumaMaxima(int [] a){
    int sumaMax = 0;
    int sumaActual = 0;
    
    for(int i = 0; int j = 0; i < a.length; i++) {
        sumaActual += a[j];


        if( sumaActual > sumaMax ) {
            sumaMax = sumaActual;
            secIni = i;
            secFin = j;
        } else if( sumaActual < 0){
            i = j + 1
            sumaActual = 0;
        }
    }

    return sumaMax;
}
```


## Problema de la búsqueda estática
La eficiencia del algoritmo para la busqueda estática depende de si el vector en el que se busca está o no ordenado-

### Búsqueda secuencial
Si no está ordenado tenemos pocas opciones a parte de la búsqueda secuencial lineal. El coste se analiza en función:

* búsqueda sin éxito --> requiere buscar en todo el vector O(N).
* caso medio de búsqueda con éxito --> N/2 que sigue siendo O(N).
* peor caso de búsqueda con éxito --> 

### Búsqueda binaria
Se puede usar en caso de que un vector esté ordenado. La búsqueda binaria se realiza desde la mitad del vector, no desde el principio.

```java
public static int busquedaBinaria(Comparable [] a, Comparable x) throws ElementoNoEncontrado {
    int inicio = 0;
    int fin = a.length -1;
    int medio;
    
    while(inicio <= fin) {
        medio = (inicio + fin) / 2;

        if( a[medio].compara(x) < 0) {
            inicio = medio + 1;
        } else if( a[medio].compara(x) > 0) {
            fin = medio - 1;
        } else {
            return medio;
        }
    }

    throw new ElementoNoEncontrado ('Busqueda binaria falla')

}
```

#### Algoritmo mejorado

```java
public static int busquedaBinaria(Comparable [] a, Comparable x) throws ElementoNoEncontrado {
    if(a.length == 0 )
        throw new ElementoNoEncontrado ('Busqueda binaria falla')
    
    int inicio = 0;
    int fin = a.length -1;
    int medio;

    while(inicio < fin) {
        medio = (inicio + fin) / 2;

        if( a[medio].compara(x) < 0) {
            inicio = medio + 1;
        } else {
            fin = medio;
        }

        if( a[inicio].compara(x) == 0) {
            return inicio;
        }
    }

    throw new ElementoNoEncontrado ('Busqueda binaria falla')

}
```

#### Asunciones

Aunque no nos hacen salir del cuadrático podemos observar que:

* Una subsecuencia negativa no puede ser no puede formar parte de la subsecuencia máxima.
* Las subsecuencias que bordeen a la máxima deben tener valores negativos o 0, si no se añadiran a la subsecuecia.



