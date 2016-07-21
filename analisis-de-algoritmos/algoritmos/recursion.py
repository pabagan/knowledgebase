# 

def imprimir_numero_en_base(base, numero):
    """ Utiliza la recursion  para espresar 
    un numero x en una base y.
    """
    
    if numero >= base:
        imprimir_base10(numero / base)
    print "%s" % (numero % base)

imprimir_numero_en_base(2, 256)
