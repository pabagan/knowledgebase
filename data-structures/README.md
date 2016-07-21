# Estructuras de datos 

## Pilas

Estructuras de datos que facilitan el acceso al último elemento insertado.

```java
public interface Pila {
    void apilar( Object x);
    Object desapilar()      throws StackEmptyException;
    Object cima()           throws StackEmptyException;
    Object cimaYDesapilar() throws StackEmptyException;
    boolean estaVacia();
    Object vaciar();
}
```

## Colas

Operar con el elemento más reciente insertado. En ellas se entra por atrás y se sale por delante.

```java
public interface Cola {
    void insertar( Object x);
    Object primero()       throws DesbordamientoInferior;
    Object quitarPrimero() throws DesbordamientoInferior;
    Object esVacia();
    Object vaciar();
}
```

Todas las colas tienen complejidad O(1).

## Listas enlazadas
## Árboles generales
## Árboles binarios de búsqueda
## Tablas hash
## Colas de prioridad
## Recursión

## Algoritmos de ordenación
### Shellsort
### Mergesort
### Quicksort
## 






seguir en 92 libro TIC 6.2.1