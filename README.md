# PSMTasks – Java Physics Simulations

This repository contains a set of Java-based physics simulations developed for the **PSM (Programming in Java with
Simulations)** course at PJAIT.

Each task focuses on a different numerical method or physical phenomenon, supported by visualization using `JFreeChart`.

---

## 📁 Structure

Each task is located under its own package inside the `src/main/java/org/psm/` directory:

- `task1/` – Sine function approximation using Taylor series.
- `task2/` – Projectile motion simulation using Euler and MidPoint methods.
- `task3/` – Pendulum motion simulation using Improved Euler (Heun) and RK4 methods, including energy conservation
  analysis.
- `task4/` - Rolling object motion simulation using MidPoint method, which allows for different inertia objects
  calculations.
- `task5/` - Moon–Earth–Sun orbital simulation using MidPoint method.
  Models Earth’s orbit around the Sun and the Moon’s local orbit around Earth,
  combining them to show realistic lunar motion.
- `task6/` – String oscillation simulation using the MidPoint method. Models wave propagation on a discretized string
  fixed at both ends, computes displacement and velocity updates over time, and visualizes shape evolution and energy
  conservation.
- `task7/` – Stationary heat distribution on a square plate using the finite difference method.
  Solves a 2D Laplace equation using linear system formulation and LU decomposition to compute the temperature profile
  given fixed edge temperatures.
- `task9/` – Fractal plant drawing using L-systems and turtle graphics.
  Implements a recursive L-system with symbolic rewriting, visualized using turtle-like drawing logic in a Swing GUI.
- `task10/` - Interactive Conway’s Game of Life simulation using Swing.
  Features a 100×100 grid of live/dead cells evolving over time, with adjustable simulation rules (underpopulation,
  overpopulation, reproduction) and cyclic boundary conditions. Cells are visually rendered in real-time using a grid of
  color-coded JLabel components.

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
    - **Wave propagation on a string fixed at both ends**
    - **Midpoint integration for both displacement and velocity**
    - **Finite difference approximation for the second spatial derivative**
      Computes and visualizes:
    - **String shape at multiple time snapshots**
    - **Kinetic, potential, and total mechanical energy over time**  
      Uses `JFreeChart` to plot the string’s spatial deformation and energy evolution.
- **Task7**:
  Solves the 2D Laplace equation for steady-state heat distribution over a square plate using:
    - **Finite difference discretization**
    - **Matrix system formulation (A·x = b)**
    - **LU decomposition with Apache Commons Math**  
      Computes and visualizes:
    - **Temperature distribution inside the plate**
    - **Color-mapped heatmap via `JFreeChart`**  
      Fixed temperatures are applied on all four edges, and the system is solved efficiently using linear algebra tools.
- **Task 9**:
  Generates and visualizes a fractal plant based on an L-system (Lindenmayer system).  
  Features:
    - **Symbol rewriting rules** with recursive growth (e.g. `"X → F+[[X]-X]-F[-FX]+X"`)
    - **Turtle graphics** for interpreting drawing instructions (`F, +, -, [, ]`)
    - Branching structures via a stack to simulate natural growth
    - Dynamic **zooming based on iteration count** to fit the drawing into view
    - Optionally supports **step-by-step rendering** for animation-like visual evolution  
      The simulation is rendered using Swing and Graphics2D.
- **Task 10**:
  Implements Conway’s Game of Life using Swing for dynamic visualization.  
  Features:
    - A **100×100 cell grid** displayed using a GridLayout with square-shaped visual cells
    - Simulation evolves in real time using a **javax.swing.Timer**
    - Implements the standard Game of Life rules:
      - Live cells survive with 2 or 3 neighbors
      - Dead cells become alive with exactly 3 neighbors
    - **Cyclic boundary conditions** allow edge-wrapping for continuous evolution
    - Users can interactively adjust rules at runtime using a control panel:
      - Change thresholds for underpopulation, overpopulation, and reproduction
      - Immediately observe how changes affect evolution
    - Visuals are color-coded:
      - Alive cells in green
      - Dead cells in black
    - Grid is initialized with a random density (configurable), ensuring meaningful patterns evolve even under strict
      Conway rules  
      This simulation demonstrates basic cellular automaton behavior and offers an interactive playground to explore how
      initial conditions and rules affect emergent complexity.

---

## 🛠️ Requirements

- Java 17+ (Java 21+ supported)
- Maven
- GUI-capable system (Swing is used for visualization)
- Dependencies:
    - [JFreeChart 1.5.5](https://github.com/jfree/jfreechart)
    - [Apache Commons Math 3.6.1](https://github.com/apache/commons-math)

---

## 📄 License

This project is open-source and available under the MIT License.