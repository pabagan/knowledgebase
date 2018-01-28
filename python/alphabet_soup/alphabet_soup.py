# O(N) complexity
def alphabet_soup(word, bowl_letters):
    """ Know if you can construct a message from 
    the letters found in your bowl.
    
    Rules:
     * soup only contains the 26 capital English 
       alphabet.  
     * could be a very large bowl of soup 
       containing many letters.  
     * each letter occurs a 0,1,many times
     * letters are ordered randomly.
    
    disclaimer: 
        maybe a bit over commented this time :)

    author  - Pablo Aguilar (pabagan@gmail.com)
    param   - string word 
    param   - string bowl_letters
    return  - boolean
    """
    
    try:
        # Create word and bowl inventories. Each one
        # build a dictionary with alphabet letters 
        # as keys and the times the letter is 
        # present.
        word_inventory = {}
        bowl_inventory = {}
        
        # Build and initialize A-Z dictionaries 
        # to 0.
        # -> O(1)
        for i in range(ord('A'), ord('Z')+1):
            word_inventory[chr(i)] = 0
            bowl_inventory[chr(i)] = 0
    
        # Build word letters inventory.
        # -> O(N)
        for letter in word:
            word_inventory[letter] += 1
            
        # Build bowl letters inventory.
        # -> O(N)
        for letter in bowl_letters:
            bowl_inventory[letter] += 1
        
        # Compare word VS bowl letters.
        # If some word letter appears 
        # more times than the bowl has, the word 
        # cannot be built.
        # -> O(1)
        for letter in word_inventory:
            if word_inventory[letter] > bowl_inventory[letter]:
                # Error clue print
                print "At least one %s is needed in the bowl." % (letter)
                
                return False

        # Exit info print 
        print "%s can be built. Great!" % (word)
        
        # Return
        return True

    except:
        print "Error! Check arguments are strictly English uppercase letters"
        
        return False


# 
# Test 
# 
bowl_letters = 'SEEUDWERNERWIWERCEORREWRN'
word = 'UNICORN'
alphabet_soup(word, bowl_letters)