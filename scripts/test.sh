#!/bin/bash
BASEDIR=$(dirname "$0")
INPUT_DIR="$BASEDIR/inputs"
OUTPUT_DIR="$BASEDIR/outputs"

javac -cp $BASEDIR/.. HangmanMain.java

run_test() {
    TEST_NUM=$1
    echo "Running test case $TEST_NUM..."
    java -cp $BASEDIR/.. HangmanMain < $INPUT_DIR/input_$TEST_NUM.txt > $OUTPUT_DIR/actual_output_$TEST_NUM.txt

    if diff -q $OUTPUT_DIR/expected_output_$TEST_NUM.txt $OUTPUT_DIR/actual_output_$TEST_NUM.txt > /dev/null; then
        echo "Test $TEST_NUM passed."
    else
        echo "Test $TEST_NUM failed."
        echo "Differences:"
        diff $OUTPUT_DIR/expected_output_$TEST_NUM.txt $OUTPUT_DIR/actual_output_$TEST_NUM.txt > $OUTPUT_DIR/diff_output_$TEST_NUM.diff
    fi
}

for i in {1..3}; do
    run_test $i
done
