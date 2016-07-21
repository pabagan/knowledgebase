# credit: https://www.python-course.eu/python3_lambda.php

#
# Not lambda
#
print("No Lambda")
def fahrenheit(T):
  return ((float(9)/5)*T + 32)
 
def celsius(T):
  return (float(5)/9)*(T-32)

temperatures = (36.5, 37, 37.5, 38, 39)

F = map(fahrenheit, temperatures)
C = map(celsius, F)

temperatures_in_Fahrenheit = list(map(fahrenheit, temperatures))
temperatures_in_Celsius = list(map(celsius, temperatures_in_Fahrenheit))

print(temperatures_in_Fahrenheit)

print(temperatures_in_Celsius)

#
# With Lambda
#
print("With Lambda")
C = [39.2, 36.5, 37.3, 38, 37.8] 
F = list(map(lambda x: (float(9)/5)*x + 32, C))
print(F)
C = list(map(lambda x: (float(5)/9)*(x-32), F))
print(C)
