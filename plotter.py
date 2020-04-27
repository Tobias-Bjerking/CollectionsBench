import pandas as pd
from matplotlib import pyplot as plt
import sys

sizes = [128, 1024,8192,16384,131072,1048576]
tests = ['iterate', 'update', 'even']
implementations = ['ONLINE_ADAPTIVE_LIST', 'ONLINE_ADAPTIVE_MAP', 'WRAPPED_MAP', 'WRAPPED_LIST']
names = {'ONLINE_ADAPTIVE_LIST': 'OnlineAdaptiveList',
         'ONLINE_ADAPTIVE_MAP': 'OnlineAdaptiveMap',
         'WRAPPED_MAP':'ConcurrentHashMap',
         'WRAPPED_LIST':'CopyOnWriteArrayList'}

#read file
df = pd.read_csv(sys.argv[1], sep=',')

plt.style.use('ggplot')

for size in sizes:
    for test in tests:
        for impl in implementations:
            ax = plt.gca()
            selected = df[(df['Param: size'] == size) & (df['Param: testType'] == test) & (df['Param: impl'] == impl)]
            grouped = selected.groupby('Threads').mean()
            grouped.plot(kind='line',marker='+',y='Score',ax=ax,label=names[impl])
        plt.legend(loc='upper left')
        plt.title(test + ": " + str(size) + " elements")
        plt.xlabel('Threads')
        plt.ylabel('Throughput')
        plt.savefig(test + "_" + str(size)+".png")
        plt.clf()


combined = {'ADAPTIVE' : ['ONLINE_ADAPTIVE_LIST', 'ONLINE_ADAPTIVE_MAP'],
          'WRAPPED' : ['WRAPPED_MAP', 'WRAPPED_LIST']}
for size in sizes:
    for merged in ['ADAPTIVE', 'WRAPPED']:
        ax = plt.gca()
        selected = df[(df['Param: size'] == size)]
        selection1 = selected[selected['Param: impl'] == combined[merged][0]]
        selection2 = selected[selected['Param: impl'] == combined[merged][1]]
        selected = pd.concat([selection1,selection2])
        grouped = selected.groupby('Threads').mean()
        grouped.plot(kind='line',marker='+',y='Score',ax=ax,label=merged)
    plt.legend(loc='upper left')
    plt.title('average' + ": " + str(size) + " elements")
    plt.xlabel('Threads')
    plt.ylabel('Throughput')
    plt.savefig('average' + "_" + str(size)+".png")
    plt.clf()



