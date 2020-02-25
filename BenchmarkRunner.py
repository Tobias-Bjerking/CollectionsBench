import sys
import os
import datetime
import subprocess

time = datetime.datetime.now()
timeformat = "%Y%m%d_%H:%M:%S"
timestamp = time.strftime(timeformat)

base_path = timestamp + "_Benchmark"
os.mkdir(base_path)

implementations = sys.argv[2]
benchmarks = sys.argv[1]

warmup_iterations = 5
iterations = 10

for i in range(1,5):
	threads = 2**i
	file_path = base_path + "/" + "testRun" + str(i) + ".csv"
	bashCommand = "java -jar benchmarks.jar {5} -p impl={4} -rff {0} -r 5 -i {1} -wi {2} -t {3} -bm thrpt".format(file_path, iterations, warmup_iterations, threads, implementations, benchmarks)

	process = subprocess.Popen(bashCommand.split(), stdout=subprocess.PIPE)
	while True:
		output = process.stdout.readline()
		if output == '' and process.poll() is not None:
			break
		if output:
			print output.strip()
	


