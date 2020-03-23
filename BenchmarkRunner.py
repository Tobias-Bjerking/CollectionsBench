import sys
import os
import datetime
import subprocess
import pandas as pd

time = datetime.datetime.now()
timeformat = "%Y%m%d_%H-%M-%S"
timestamp = time.strftime(timeformat)

base_path = timestamp + "_Benchmark"
os.mkdir(base_path)

# No default values for the implementation and benchmark variables
num_arguments = len(sys.argv)
if num_arguments < 3:
    print "You need to supply the following arguments: \n benchmarks implementations test_type [warmup_iterations iterations]\n"

benchmarks = sys.argv[1] if num_arguments > 1 else 'Online'
implementations = ('-p impl=' + sys.argv[2]) if num_arguments > 2 else ''
warmup_iterations = sys.argv[3] if num_arguments > 3 else 20
iterations = sys.argv[4] if num_arguments > 4 else 40

for t in ['even', 'update', 'iterate']:
	for i in range(1, 4):
		threads = 2**i
		file_path = base_path + "/bench_" + benchmarks + '-testType_' + t + "_threads-"+2**i+ ".csv"
		bashCommand = "java -jar benchmarks.jar .*{4}.* {3} -p testType={5}  -rff {0} -r 5 -i {1} -wi {2} -t {6} -bm thrpt".format(file_path, iterations, warmup_iterations, implementations, benchmarks, t, threads)

		process = subprocess.Popen(bashCommand.split(), stdout=subprocess.PIPE)
		while True:
			output = process.stdout.readline()
			if output == '' and process.poll() is not None:
				break
			if output:
				print output.strip()


#combine files.
all_files = glob.glob(os.path.join(".", "*.csv"))
all_files = [x for x in all_files if x != "./merged.csv"]
f = open("merged.csv", "a")

f1 = open(all_files[0])
first_row = f1.readline()
f1.close()
f.write(first_row)

for x in all_files:
    first = False
    y = open(x)
    for line in y:
        if first:
            f.write(line)
        else:
            first = True
    y.close()
f.close()

