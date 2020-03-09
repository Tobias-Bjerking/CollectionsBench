import sys
import os
import datetime
import subprocess

time = datetime.datetime.now()
timeformat = "%Y%m%d_%H-%M-%S"
timestamp = time.strftime(timeformat)

base_path = timestamp + "_Benchmark"
os.mkdir(base_path)

# No default values for the implementation and benchmark variables
num_arguments = len(sys.argv)
if num_arguments < 3:
    print "You need to supply the following arguments: \n benchmarks implementations test_type [warmup_iterations iterations]\n"
    sys.exit()

implementations = sys.argv[2]
benchmarks = sys.argv[1]
test_type = sys.argv[3]

warmup_iterations = sys.argv[4] if num_arguments > 3 and sys.argv[4] is not None else 20
iterations = sys.argv[5] if num_arguments > 4 and sys.argv[5] is not None else 40

for i in range(1,5):
	threads = 2**i
	file_path = base_path + "/bench_" + benchmarks + "-threads_" + str(threads) + "-datastruct_" + implementations + ".csv"
	bashCommand = "java -jar benchmarks.jar .*{5}.* -p impl={4} -p testType={6}  -rff {0} -r 5 -i {1} -wi {2} -t {3} -bm thrpt" .format(file_path, iterations, warmup_iterations, threads, implementations, benchmarks, test_type)

	process = subprocess.Popen(bashCommand.split(), stdout=subprocess.PIPE)
	while True:
		output = process.stdout.readline()
		if output == '' and process.poll() is not None:
			break
		if output:
			print output.strip()
	


