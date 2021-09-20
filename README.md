# README
# About Onboarding

### Running the project:
To build use:
`mvn package`

To run use:
`./run`

This will give you a barebones REPL, where you can enter text and you will be output at most 5 suggestions sorted alphabetically. :0

To start the server use:
`./run --gui [--port=<port>]`

### Main.java
This class contains the REPL, which parses the user's input and calls methods or reprompts the user accordingly.
The REPL utilizes five commands as follows:
* `add <double> <double>`
* `subtract <double> <double>`
* `stars <filename>`
  * Where `<filename>` is the path file to a stars data
* `naive_neighbors <k> <x> <y> <z>` 
  * Where `k` is the nonnegative, integral number of nearest neighbors to find
  and `x, y, z` represents the position from which to find the nearest neighbors.
* `naive_neighbors <k> <“name”>` 
  * Where `name` is the name of a star, which must be given in quotes.

### MathBot.java
This class contains the methods for `add` and `subtract`.

### StarProcessor.java
This class contains the methods for both version of `naive_neighbors` as well as the CSV reader/parser called by `stars <filename>`.


### Star.java
This class contains the `starID` and distance from inputted origin. It is used in conjunction
with `StarProcessor.java` to execute the `naive_neighbors` methods.
### SortByDistance.java
`Star.java` also contains the `SortByDistance` class which implements the `Comparator` method. This class is used for
sorting stars from smallest distance to largest distance in `StarProcessor.java`.

# Testing Onboarding:
This project utilizes unit testing and system testing. However, the current testing is not complete, as I did not test
all the edge cases of `stars <filename>` and both `naive_neighbors` methods. For the tests I did write,
the output is not correctly formatted and sometimes throws an error when it's not suppose to.
## Unit Testing:
### MathBotTest.java
This test file tests the functionality and edge cases of the `add` and `subtract` methods.
### StarProcessorTest.java
This test file tests the functionality of reading a stars CSV file storing the information properly in `ArrayLists`.

## System Testing:
The system tests that passed: `adding.test`, `error.test`, `multiplecommands.test`, `subtraction.test`, `no_stars_file.test`

The system tests that failed: `0_neighbors.test`, `1_star.test`, `5_star_n_name.test`, `exclude_star.test`, `incorrect_command.test`.
As far as I've seen, these failures have resulted from my methods outputs not being read correctly. The error for each failed
test is `"ERROR: We couldn't process your input"`, which is thrown in `Main.java`.


