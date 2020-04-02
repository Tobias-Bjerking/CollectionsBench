import pandas as pd
from matplotlib import pyplot as plt
import sys

#read file
df = pd.read_csv(sys.argv[1])

plt.style.use('ggplot')


for size in [128, 1024,8192,16384,131072,1048576]:
    for test in ['iterate', 'iteratepure', 'update', 'even']:
        ax = plt.gca()
        selected = df[(df['Param: size'] == size) & (df['Param: testType'] == test)]
        selected.groupby(['Param: impl', 'Param: testType']).plot(kind='line',x='Threads',y='Score',ax=ax)
        plt.savefig(test + "_" + str(size)+".png")
        plt.clf()

