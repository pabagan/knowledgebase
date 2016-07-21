# 
# The Binary Search
# 
# cretid: https://interactivepython.org/runestone/static/pythonds/SortSearch/TheBinarySearch.html

def binary_search(vector, item):
    inicio = 0
    fin = len(vector)-1
    encontrado = False

    while inicio<=fin and not encontrado:
        medio = (inicio + fin)//2
        if vector[medio] == item:
            encontrado = True
        else:
            if item < vector[medio]:
                fin = medio-1
            else:
                inicio = medio+1
    
    return encontrado
    
testlist = [0, 1, 2, 8, 13, 17, 19, 32, 42,]
print binary_search(testlist, 3)
print binary_search(testlist, 13)
