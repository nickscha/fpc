# fpc

The **Fast Project Creator** is a personal tool designed to quickly generate a new project structure. It's built to streamline the setup process for new C projects, pre-populating them with common elements like **GitHub Actions**, **documentation templates**, and a consistent file structure. This saves you from the repetitive work of setting up boilerplate code and configuration files for each new project.

---

## Key Features

* **Automated Project Generation:** Rapidly creates a complete project directory.
* **Customizable Templates:** Uses a C89 `nostdlib` template, allowing you to easily create portable C projects without relying on the standard library.
* **Placeholder Replacements:** Prompts you for key project details like the author, project name, and version, then automatically replaces placeholders in the generated files.
* **Integrated GitHub Actions:** Sets up a basic CI/CD workflow, ready to go for your new GitHub repository.

---

## Quick Start

### Prerequisites

To run FPC, you only need the **Java Development Kit (JDK)** installed on your system.

### Running the Tool

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/nickscha/fpc.git](https://github.com/nickscha/fpc.git)
    cd fpc
    ```
2.  **Run the Java file:**
    ```sh
    java FPC.java
    ```
    This will launch an interactive prompt in your terminal.
3.  **Configure your project:**
    Follow the on-screen prompts to customize your project details. You can either press `Enter` to accept the default value for each field or type a new value.

    ```sh
    [fpc] ###########################################################
    [fpc] Press Enter to keep the default value or enter a new value.
    [fpc] ###########################################################
    [fpc] {{OUTPUT_DIR}}       [default: target              ]: my-awesome-project
    [fpc] {{AUTHOR}}           [default: nickscha            ]: Your Name
    [fpc] {{YEAR}}             [default: 2025                ]: 
    [fpc] {{VERSION}}          [default: v0.1                ]: 1.0.0
    [fpc] {{LIB_NAME_SHORT}}   [default: vgg                 ]: mylib
    [fpc] {{LIB_NAME_LONG}}    [default: Vector Graphics generator]: My Awesome Library
    [fpc] {{DESCRIPTION}}      [default: A C89 standard compliant, single header, nostdlib (no C Standard Library) Vector Graphics generator (VGG).]: A new library for doing awesome stuff.
    [fpc] finished
    ```

    After you complete the prompts, FPC will create a new directory (e.g., `my-awesome-project`) populated with your new project structure.
