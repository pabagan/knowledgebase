#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Make CSV from PRODUCTOS file received 
by our dear copy 
"""
import re
import csv
from collections import defaultdict

def file_to_array(file_location):
    fhand = open(file_location)
    file_to_list = []
    # Loop to clean lines and 
    # whitespaces 
    for line in fhand:
        # jump empty lines
        if re.match(r'^\s*$', line):
            continue
        
        # strip white space
        file_to_list.append(line.rstrip())

    return file_to_list

def line_checker(line, start):
    if line.startswith(start):
        return True
    return False

def is_header(line):
    if line.startswith('# ----------------------'):
        return True
    return False

def is_section_start(line):
    if line.startswith('# title:') or line.startswith('# description:') or line.startswith('# id:') :
        return True
    return False

def make_id(line):
    return line[6:]

def create_model(file_to_list):
    n = 0
    i = 0
    j = 0
    productos_model = defaultdict(dict)
    while i < len(file_to_list):
        '''
        Loop in clean file creating a list per field
        '''
        
        #print file_to_list[i]
        #print 'i:', i,'j:', j
        if is_header(file_to_list[i]):
            j = i + 1
            
            #print file_to_list[j]

            while j < len(file_to_list):
                if is_header(file_to_list[j]):
                    n = n + 1
                    i =  j - 1
                    break
                else:
                    if line_checker(file_to_list[j], '# id:'):
                        productos_model[n]['id'] = make_id(file_to_list[j])
                    if line_checker(file_to_list[j], '# title:'):
                        productos_model[n]['title'] = file_to_list[j + 1]
                    if line_checker(file_to_list[j], '# shortDescription:'):
                        productos_model[n]['short_description'] = []
                        productos_model[n]['short_description'].append(file_to_list[j+1])
                        productos_model[n]['short_description'].append(file_to_list[j+2])
                        productos_model[n]['short_description'].append(file_to_list[j+3])
                    if line_checker(file_to_list[j], '# description:'):
                        productos_model[n]['description'] = file_to_list[j + 1]
                    #break

                j = j + 1
        
        i = i + 1

    return productos_model

def list_to_hmlt_list(pyList):
    o = '<ul>'
    for item in pyList:
        o += '<li>' + item[2:] + '</li>'
    o += '</ul>'

    return o

def create_product_csv(productos_model):
    i = 0 
    with open('productos.csv', 'w') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=';',
                                quotechar='|', quoting=csv.QUOTE_MINIMAL)
        # CSV header
        spamwriter.writerow(['id','nombre','sort desc','description'])
        
        # CSV body
        for i in range(len(productos_model)):
            row = []
            row.append(productos_model[i]['id'])
            row.append(productos_model[i]['title'])
            row.append(list_to_hmlt_list(productos_model[i]['short_description']))
            row.append(productos_model[i]['description'])
            spamwriter.writerow(row)

            i = i + 1

def main():
    filename = "PRODUCTOS.txt"
    filename = "PRODUCTOS.v2.txt"
    file_to_list = file_to_array('./'+filename)
    productos_model = create_model(file_to_list)

    # Create CSV
    create_product_csv(productos_model)

if __name__ == "__main__":
    main()