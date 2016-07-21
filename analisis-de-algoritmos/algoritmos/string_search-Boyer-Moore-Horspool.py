# bmh.py
#
# An implementation of Boyer-Moore-Horspool string searching.
#
# This code is Public Domain.
# see: http://code.activestate.com/recipes/117223-boyer-moore-horspool-string-searching/
#
def BoyerMooreHorspool(pattern, text):
    m = len(pattern)
    n = len(text)
    
    print '-------------------------------------------------------'
    print 'ASIGNACIONES de m y n'
    print 'm len(pattern)', m
    print 'n text(text)', n
    
    if m > n: return -1


    
    print '-------------------------------------------------------'
    print 'Crear lista de 256 y iniciarla al valor de m skip[]'
    skip = []
    for k in range(256): 
        skip.append(m)
    #print skip

    print '-------------------------------------------------------'
    print 'Variar la lista con un for en el range del pattern y actualizar elmentos de la lista con m - k - 1'
    print 'for k in range(m - 1): ', range(m - 1)
    for k in range(m - 1): 
        print 'm:', m, 'k:', k
        print k, pattern[k], ord(pattern[k]), 'm - k - 1: ', m - k - 1

        skip[ord(pattern[k])] = m - k - 1

    #print skip
    #print skip[116]
    #print skip[104]
    #print skip[105]

    print '-------------------------------------------------------'
    print 'Transform skip to tupple (inmutable)'
    
    skip = tuple(skip)
    
    print '-------------------------------------------------------'
    print 'Loop con k = m - 1', 'mientras que k < n'
    k = m - 1
    print 'k:', k
    while k < n:
        j = m - 1
        i = k
        print 'k:', k, ', j:', j, ', i:', i

        print '-------------------------------------------------------'
        print 'Loop while j >= 0 and text[i] == pattern[j]:'
        while j >= 0 and text[i] == pattern[j]:
            print text[i], pattern[j]
            print 'j:', j, 'i:', i
            j -= 1
            i -= 1

        print '-------------------------------------------------------'
        print 'Si j == -1: return i + 1'
        if j == -1: 
            return i + 1
        
        print '-------------------------------------------------------'
        print 'Increment k from:', k
        k += skip[ord(text[k])]
        print 'to:', skip[ord(text[k])]

    return -1

if __name__ == '__main__':
    text = "this is the string to search in"
    pattern = "the"
    s = BoyerMooreHorspool(pattern, text)
    print 'Text:',text
    print 'Pattern:',pattern
    
    if s > -1:
        print 'Pattern \"' + pattern + '\" found at position',s
    else:
        print 'Pattern \"' + pattern + '\" not found.'
