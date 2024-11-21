<h1 align="center" style="font-weight: bold;">Antivirus Application 🦠</h1>

<p align="center"> 
  <a href="#tech">Technologies</a> • 
  <a href="#features">Features</a> • 
  <a href="#started">Getting Started</a> • 
  <a href="#contribute">Contribute</a> 
</p> 

<p align="center"> 
  <b>A Java-based antivirus application for scanning directories, detecting target files, and allowing file deletion.</b> 
</p>

<h2 id="tech">💻 Technologies</h2>

- **Java**
- **ExecutorService** (for multithreading)
- **AtomicBoolean** (for thread-safe boolean flags)
- **File I/O** (for scanning and deleting files)

<h2 id="features">🛠️ Features</h2>

<h4>1. Directory Scanning</h4>
<p>The program allows the user to select a directory, and from there, the system performs a recursive scan of files and subdirectories.</p>

<h4>2. File Detection</h4>
<p>The system can be configured to detect files with specific extensions or names. Detected files are logged for review.</p>

<h4>3. File Deletion</h4>
<p>After detecting files, the user can opt to delete all detected files. The program will prompt the user for confirmation before proceeding with the deletion.</p>

<h4>4. Real-time Logging</h4>
<p>The program displays detailed logs of every action performed during the scanning and deletion process, allowing the user to monitor progress and any issues encountered.</p>


<h2 id="started">🚀 Getting Started</h2>

How to run the project locally

<h3>Prerequisites</h3>

Ensure the following are installed on your system:

- [**Java 8+**](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)

<h3>Installation</h3>

1. Clone the repository:

```bash
git clone https://github.com/leovncs/antivirus-app.git
```

2. Navigate to the project directory:

```bash
cd antivirus-app
```

3. Compile the project:

```bash
javac src/*.java src/main/java/scanner/*.java src/main/java/utils/*.java
```

4. Run the application:

```bash
java Main
```

<h2 id="contribute">📫 Contribute</h2>

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.
