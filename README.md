# Automata Project Documentation

## Project Overview
This project implements a finite automata system with various operations and functionalities. The system allows users to create, manipulate, and analyze different types of automata.

## Project Structure

### Main Components

#### Model Package (`com.automataproject.model`)
Contains the core data structures of the automata system:

- **Automata**: The main class representing a finite automaton
  - Manages nodes and transitions
  - Handles serialization for saving/loading automata
  - Maintains a list of all created automata
  - Supports deterministic and non-deterministic automata

- **Node**: Represents a state in the automaton
  - Can be marked as initial or final state
  - Contains transitions to other nodes
  - Maintains relationships with parent automaton

- **Transition**: Represents a transition between states
  - Contains the transition symbol
  - Links source and destination nodes
  - Supports serialization

#### Commands Package (`com.automataproject.commands`)
Implements the command pattern for various automata operations:

- **CommandManager**: Manages and executes different commands
- **Command**: Interface defining the contract for all commands

Available commands include:
1. Create Automata
2. Check Transition Information
3. Save to File
4. Read from File
5. Check Empty Language
6. Print All Automata
7. Display Automata
8. Check Deterministic
9. Recognize Automata
10. Create Automata from Regex

#### Services Package (`com.automataproject.services`)
Contains business logic and utility services for the automata system.

## Features
- Create and manage finite automata
- Support for both deterministic and non-deterministic automata
- Save and load automata from files
- Check language properties
- Convert regular expressions to automata
- Visualize automata
- String recognition

## Usage
The system provides a command-line interface for interacting with automata. Users can:
1. Create new automata
2. Add nodes and transitions
3. Save automata to files
4. Load automata from files
5. Check various properties of automata
6. Test string recognition
7. Convert regular expressions to automata

## Technical Details
- Written in Java
- Uses serialization for persistence
- Implements the Command pattern for operations
- Supports a predefined alphabet of characters (a-z, 0-9) 