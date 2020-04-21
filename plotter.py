import pandas as pd
from matplotlib import pyplot as plt
import sys

#read file
df = pd.read_csv(sys.argv[1], sep=',')

plt.style.use('ggplot')

for size in [128, 1024,8192,16384,131072,1048576]:
    for test in ['iterate', 'update', 'even']:
        for impl in ['ADAPTIVE', 'WRAPPED_MAP', 'WRAPPED_LIST']:
            ax = plt.gca()
            selected = df[(df['Param: size'] == size) & (df['Param: testType'] == test) & (df['Param: impl'] == impl)]
            grouped = selected.groupby('Threads').mean()
            grouped.plot(kind='line',y='Score',ax=ax,label=impl)
        plt.legend(loc='upper left')
        plt.savefig(test + "_" + str(size)+".png")
        plt.clf()

