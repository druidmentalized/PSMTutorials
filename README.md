# PSMTasks – Java Physics Simulations

This repository contains a set of Java-based physics simulations developed for the **PSM (Programming in Java with Simulations)** course at PJAIT.

Each task focuses on a different numerical method or physical phenomenon, supported by visualization using `JFreeChart`.

---

## 📁 Structure

Each task is located under its own package inside the `src/main/java/org/psm/` directory:

- `task1/` – Sine function approximation using Taylor series.
- `task2/` – Projectile motion simulation using Euler and MidPoint methods.
- `task3/` – Pendulum motion simulation using Improved Euler (Heun) and RK4 methods, including energy conservation analysis.

---

## 🔍 Topics Covered

- **Task 1**:  
  Numerical approximation of the sine function using **Taylor series expansion**.  
  Compares the approximation with Java’s built-in `Math.sin`.

- **Task 2**:  
  Simulates **2D projectile motion** under gravity and air resistance.
    - Implements **Euler's method** and the **Midpoint method**.
    - Visualizes trajectories using `JFreeChart`.

- **Task 3**:  
  Simulates a **mathematical pendulum** using:
    - **Improved Euler method (Heun's method)**
    - **Runge-Kutta 4th order (RK4)**  
      Calculates and plots:
    - **Potential energy**
    - **Kinetic energy**
    - **Total mechanical energy**
      to compare conservation behavior across methods.

---

## 🛠️ Requirements

- Java 17+ (Java 21+ supported)
- Maven
- GUI-capable system (Swing is used for visualization)
- Dependencies:
    - [JFreeChart 1.5.5](https://github.com/jfree/jfreechart)

---

## 📄 License

This project is open-source and available under the MIT License.