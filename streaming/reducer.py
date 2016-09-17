#!/usr/bin/python

import sys

maxTemp=-9999
maxTempYear=""
for line in sys.stdin:
    currYear, currTemp = line.split(' ')

    if maxTempYear != '' and maxTempYear != currYear:
       print maxTempYear,maxTemp
       maxTemp = int(currTemp)
       maxTempYear = currYear

    if int(currTemp) > maxTemp and int(currTemp) != 9999:
       maxTemp = int(currTemp)
       maxTempYear = currYear

print maxTempYear,maxTemp
