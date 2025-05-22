package org.psm.task10;

public class GameOfLifeSimulator {

    private final int GRID_SIZE;
    private boolean[][] cellsAlive;

    private final int[][] directions = new int[][]{
            {-1, -1},
            {0, -1},
            {1, -1},
            {-1, 0},
            {1, 0},
            {-1, 1},
            {0, 1},
            {1, 1}
    };

    public GameOfLifeSimulator(boolean[][] cellsAlive) {
        this.cellsAlive = cellsAlive;
        this.GRID_SIZE = cellsAlive.length;
    }

    public void makeEvolutionStep(int underpopulationThreshold, int overpopulationThreshold, int reproductionThreshold) {
        boolean[][] nextGen = new boolean[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int aliveAround = aliveAround(cellsAlive, j, i);
                nextGen[i][j] = willEvolve(cellsAlive[i][j], aliveAround, underpopulationThreshold, overpopulationThreshold, reproductionThreshold);
            }
        }

        for (int i = 0; i < GRID_SIZE; i++) {
            System.arraycopy(nextGen[i], 0, cellsAlive[i], 0, GRID_SIZE);
        }
    }

    private int aliveAround(boolean[][] cellsAlive, int x, int y) {
        int aliveAround = 0;

        for (int[] direction : directions) {
            int newX = (x + direction[0] + GRID_SIZE) % GRID_SIZE;
            int newY = (y + direction[1] + GRID_SIZE) % GRID_SIZE;

            if (cellsAlive[newX][newY]) aliveAround++;
        }

        return aliveAround;
    }

    private boolean willEvolve(boolean alive, int aliveAround, int underpopulation, int overpopulation, int reproduction) {
        if (alive) {
            if (aliveAround < underpopulation) {
                return false;
            }
            else return aliveAround <= overpopulation;
        }
        else {
            return aliveAround == reproduction;
        }
    }
}
