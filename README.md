# PSMTasks – Java Physics Simulations

This repository contains a set of Java-based physics simulations developed for the **PSM (Programming in Java with Simulations)** course at PJAIT.

Each task focuses on a different numerical method or physical phenomenon, supported by visualization using `JFreeChart`.

---

## 📁 Structure

Each task is located under its own package inside the `src/main/java/org/psm/` directory:

- `task1/` – Sine function approximation using Taylor series.
- `task2/` – Projectile motion simulation using Euler and MidPoint methods.
- `task3/` – Pendulum motion simulation using Improved Euler (Heun) and RK4 methods, including energy conservation analysis.
- `task4/` - Rolling object motion simulation using MidPoint method, which allows for different inertia objects calculations. 
- `task5/` - Moon–Earth–Sun orbital simulation using MidPoint method.
  Models Earth’s orbit around the Sun and the Moon’s local orbit around Earth,
  combining them to show realistic lunar motion.
- `task6/` – String oscillation simulation using the MidPoint method. Models wave propagation on a discretized string
  fixed at both ends, computes displacement and velocity updates over time, and visualizes shape evolution and energy 
  conservation.

---

## 🔍 Topics Covered

- **Task 1**:  
  - Numerical approximation of the sine function using **Taylor series expansion**.  
  - Compares the approximation with Java’s built-in `Math.sin`.

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

- **Task 4**:
  Simulates a rolling object (e.g. solid sphere or cylinder) descending an inclined plane without slipping.
  Implements the Midpoint method (improved Euler) to integrate:
  - **Translational motion along the incline**
  - **Rotational motion due to rolling**  
  Calculates and visualizes:
  - **Center of mass trajectory**
  - **Rotational point trajectory**
  - **Inclined surface geometry**
  - **Ball outline (circle projection at start)**
  - **Potential, kinetic (translational + rotational), and total energies**  
  Uses `JFreeChart` to plot all spatial and energetic data**

- **Task 5**:
    Simulates the Moon–Earth–Sun orbital system using the Midpoint method.  
    Models:
    - **Earth’s orbit around the Sun using Newtonian gravity**
    - **Moon’s local orbit around Earth, treated as a two-body system**
    Combines both to produce a realistic lunar trajectory in global coordinates and outputs:
    - **Earth and Moon orbits**
    - **Fixed Sun position**  
    Uses `JFreeChart` for clear spatial visualization.
- **Task 6**:
     Simulates transverse oscillations of a discrete string using the Midpoint method.  
     Models:
     -	**Wave propagation on a string fixed at both ends**
     -	**Midpoint integration for both displacement and velocity**
     -	**Finite difference approximation for the second spatial derivative**
     Computes and visualizes:  
     - **String shape at multiple time snapshots**
     - **Kinetic, potential, and total mechanical energy over time**  
     Uses `JFreeChart` to plot the string’s spatial deformation and energy evolution.
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