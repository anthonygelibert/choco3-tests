package fr.gelibert.tests.choco;

import samples.AbstractProblem;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

import java.util.Calendar;

/**
 * My own magic square.
 *
 * @author Anthony GELIBERT
 * @version 0.0.1
 */
public final class MagicSquare extends AbstractProblem {
    /** Size of the square. */
    private static final int      SQUARE_SIZE = 5;
    /** All the vars. */
    private final        IntVar[] m_allVars = new IntVar[SQUARE_SIZE * SQUARE_SIZE];

    @Override
    public void createSolver() {
        solver = new Solver("My own magic square");
    }

    @Override
    public void buildModel() {
        final IntVar sum = VariableFactory.fixed((SQUARE_SIZE * ((SQUARE_SIZE * SQUARE_SIZE) + 1)) / 2, solver);
        final IntVar[][] square = new IntVar[SQUARE_SIZE][SQUARE_SIZE];
        final IntVar[][] tsquare = new IntVar[SQUARE_SIZE][SQUARE_SIZE];

        // Variables
        int k = 0;
        for (int i = 0; i < SQUARE_SIZE; i++) {
            for (int j = 0; j < SQUARE_SIZE; j++, k++) {
                tsquare[j][i] = square[i][j] = m_allVars[k] = VariableFactory.enumerated(String.valueOf((i * SQUARE_SIZE) + j), 1, SQUARE_SIZE * SQUARE_SIZE, solver);
            }
        }

        final IntVar[] diag1 = new IntVar[SQUARE_SIZE];
        final IntVar[] diag2 = new IntVar[SQUARE_SIZE];
        for (int i = 0; i < SQUARE_SIZE; i++) {
            diag1[i] = square[i][i];
            diag2[i] = square[SQUARE_SIZE - 1 - i][i];
        }

        // Constraints
        // C1 : differences.
        solver.post(IntConstraintFactory.alldifferent(m_allVars, "BC"));
        // C3 : sums.
        for (int i = 0; i < SQUARE_SIZE; i++) {
            solver.post(IntConstraintFactory.sum(square[i], sum));
            solver.post(IntConstraintFactory.sum(tsquare[i], sum));
        }
        solver.post(IntConstraintFactory.sum(diag1,sum));
        solver.post(IntConstraintFactory.sum(diag2, sum));
        // C4 : sym.
        solver.post(IntConstraintFactory.arithm(square[0][0], ">", square[SQUARE_SIZE-1][0]));
        solver.post(IntConstraintFactory.arithm(square[0][0], "<", square[0][SQUARE_SIZE - 1]));
        solver.post(IntConstraintFactory.arithm(square[0][0], ">", square[SQUARE_SIZE - 1][SQUARE_SIZE - 1]));
    }

    @Override
    public void configureSearch() {
        //solver.set(IntStrategyFactory.domOverWDeg_InDomainMin(m_allVars, 0));
        solver.set(IntStrategyFactory.ImpactBased(m_allVars, Calendar.getInstance().getTimeInMillis()));
    }

    @Override
    public void solve() {
        solver.findSolution();
    }

    @Override
    public void prettyOut() {
    }

    public static void main(final String... args) {
        new MagicSquare().execute(args);
    }
}
