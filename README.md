# BaseballElimination

## Description
BaseballElimination is a Java program designed to solve the baseball elimination problem. It analyzes the wins, losses, and remaining games of teams during a season to determine which teams are mathematically eliminated from winning the league.

## Features
- **Early Elimination Detection**: The program first checks whether any teams are eliminated based on the maximum possible number of wins compared to the current wins of other teams.
- **Flow Network Analysis**: For teams that cannot be determined by early elimination detection, the program constructs a flow network and applies the Ford-Fulkerson algorithm to ascertain their elimination status.

## Dependencies
- Java Development Environment
- [algs4.jar](https://algs4.cs.princeton.edu/code/) library for providing the Ford-Fulkerson algorithm and other related data structures.

## Installation
Ensure Java is installed on your system and `algs4.jar` is included in your project's classpath.
1. **Download: Click on this [link](http://algs4.cs.princeton.edu/code/algs4.jar) to download the jar file**
2. **Move: Open the terminal and navigate to the directory where the file ”algs4.jar” was saved.**
3. **Extract: From the correct directory, run: jar xf algs4.jar**
4. **New Files: You should see the following files now in this directory: META-INF/, TestAlgs4 class, TestAlgs4.java, edu/, COPYING; where the to files ending with ”/” are directories.**
5. **Test: Check the installation by running: java -cp algs4.jar TestAlgs4**

## Usage
1. **Compile the Program**:
   ```bash
   javac -cp .;path/to/algs4.jar BaseballElimination.java
2. **Rune the Program**:
   ```bash
   java -cp .;path/to/algs4.jar BaseballElimination
3. **Using an Input File**:
   ```bash
   java -cp .;path/to/algs4.jar BaseballElimination path/to/inputfile.txt


### Automated Testing Script
An included Bash script `test_script.sh` automates testing across multiple input files and compares the program's output with expected results.

- **Script Usage**:
  - Place test files in the `test_files` directory.
  - Ensure each test file has a corresponding `_output.txt` file containing the official expected output.
  - Run the script:
    ```bash
    bash test_script.sh
    ```
  - Check the `test_files/my_result.txt` file for test outcomes and `PASS`/`FAIL` status.
