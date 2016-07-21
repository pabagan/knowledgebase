# Mejora del algoritmos de O(N**3), O(N**2)
# a O(N)


# Data una secuencia de enteros (posiblemente negativos) 
# `A1, A2, ..., An` encontrar (identificando la 
# subsecuencia) el valor maximo y la subsecuencia 
# correspondiente. Si todos son negativos la subsecuencia 
# esta vacia y siendo la suma 0.


# Demo sequenzes
secuencia1 = [-2, 11, -4, 13, -5, 2]    # result OK 20
secuencia2 = [1, -3, 4, -2, -1, 6]      # result OK 7


# 
# V Cubic O(N**3)
# 
def subsecuencia_suma_maxima_cubic( secuencia ):
    sumaMax = 0
    # loop1
    i = 0
    while (i < len(secuencia)):
        j = i

        while (j < len(secuencia)):
            k = i 
            sumaActual = 0 

            while (k <= j):
                sumaActual += secuencia[k]

                if (sumaActual > sumaMax):
                    sumaMax = sumaActual
                    seccIni = i
                    seccFin = j
                
                
                k += 1
            j += 1
        i += 1

    return sumaMax

print 'Cubic'
print subsecuencia_suma_maxima_cubic( secuencia1 )
print subsecuencia_suma_maxima_cubic( secuencia2 )


#
# V Cuadratic O(N**2)
#
def subsecuencia_suma_maxima_cuatratic( secuencia ):
    sumaMax = 0
    k = 0
    
    # loop1
    i = 0
    while (i < len(secuencia)):
        sumaActual = 0 

        j = i
        while (j < len(secuencia)):
            sumaActual += secuencia[j]

            if (sumaActual > sumaMax):
                sumaMax = sumaActual
                seccIni = i
                seccFin = j

            j += 1
        i += 1

    return sumaMax


print 'Cuadratic'
print subsecuencia_suma_maxima_cuatratic( secuencia1 )
print subsecuencia_suma_maxima_cuatratic( secuencia2 )

#
# Lineal O(N)
#
def subsecuencia_suma_maxima_lineal( secuencia ):
    sumaMax = 0
    sumaActual = 0 
    
    i = 0
    j = 0
    # loop
    while (j < len(secuencia)):

        sumaActual += secuencia[j]

        if (sumaActual > sumaMax):
            sumaMax = sumaActual
            seccIni = i
            seccFin = j
            #print 'ini:', seccIni, 'fin:', seccFin
            print 'i:', i, 'j:', j

        elif (sumaActual < 0):
            i = j + 1
            sumaActual = 0 
            print 'i:', i, 'j:', j
        
        j += 1

    return sumaMax

# test
secuencia1 = [-2, 11, -4, 13, -5, 2]    # result OK 20
secuencia2 = [1, -3, 4, -2, -1, 6]      # result OK 7

print 'Lineal'
print subsecuencia_suma_maxima_lineal( secuencia1 )
print subsecuencia_suma_maxima_lineal( secuencia2 )
