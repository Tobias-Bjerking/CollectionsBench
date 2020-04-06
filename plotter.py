import pandas as pd
from matplotlib import pyplot as plt
import sys

#read file
df = pd.read_csv(sys.argv[1])

plt.style.use('ggplot')


for size in [128, 1024,8192,16384,131072,1048576]:
    for test in ['iterate', 'update', 'even']:
        for impl in ['ONLINE_ADAPTIVE_LIST', 'ONLINE_ADAPTIVE_MAP', 'WRAPPED_MAP', 'WRAPPED_LIST']:
            ax = plt.gca()
            selected = df[(df['Param: size'] == size) & (df['Param: testType'] == test) & (df['Param: impl'] == impl)]
            sorted = selected.sort_values(['Threads'])
            sorted.plot(kind='line',x='Threads',y='Score',ax=ax,label=impl)
        plt.legend(loc='upper left')
        plt.savefig(test + "_" + str(size)+".png")
        plt.clf()

