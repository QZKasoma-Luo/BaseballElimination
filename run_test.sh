#!/bin/bash

# Compile the Java program
javac BaseballElimination.java

# Set the path to the test files directory
test_folder_path="test_files"  # always put the relative path here

# Set the path to the output file
output_file="${test_folder_path}/my_result.txt"

# Clear the output file
> "$output_file"

# Run the Java program for each test file and append the results to the output file
for infile in "${test_folder_path}"/teams*; do
  # Exclude any answer files or the output file itself
  if [[ "$infile" != *_output.txt ]] && [[ "$(basename "$infile")" != "my_result.txt" ]]; then
    # Append the name of the test file to the output file for clarity
    echo "Results for $(basename "$infile"):" >> "$output_file"
  
    # Execute the Java program with the test file as a parameter and append the output to the result file
    java BaseballElimination "$infile" >> "$output_file"
  
    # Insert a delimiter to visually separate the results of different test files in the result file
    echo "----------------------------------------------" >> "$output_file"
  fi
done

# Announce completion
echo "All results have been written to $output_file"
