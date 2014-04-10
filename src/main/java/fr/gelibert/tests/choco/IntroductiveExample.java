package fr.gelibert.tests.choco;

import samples.AbstractProblem;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

import java.util.Calendar;

/**
 * Introductive example.
 *
 * @author Anthony GELIBERT
 * @version 1.0.1
 */
public final class IntroductiveExample extends AbstractProblem {
    IntVar[] m_allVars = new IntVar[5];

    @Override
    public void createSolver() {
        solver = new Solver("Introductive Example");
    }

    @Override
    public void buildModel() {
        // Variables
        final IntVar xa = VariableFactory.enumerated("Xa", new int[]{1, 2}, solver);/* NON-NLS */
        final IntVar xb = VariableFactory.enumerated("Xb", new int[]{1, 2, 3, 4, 5}, solver);/* NON-NLS */
        final IntVar xc = VariableFactory.enumerated("Xc", new int[]{1, 2, 3}, solver);/* NON-NLS */
        final IntVar xd = VariableFactory.enumerated("Xd", new int[]{1, 2, 3, 4, 5}, solver);/* NON-NLS */
        final IntVar d = VariableFactory.enumerated("d", new int[]{1, 2}, solver);/* NON-NLS */

        m_allVars = new IntVar[]{xa,xb,xc,xd,d};
        // Constraints
        solver.post(IntConstraintFactory.alldifferent(new IntVar[]{xa, xb, xc, xd}, "BC")); /* NON-NLS */
        solver.post(IntConstraintFactory.arithm(xc, "<", xd));
        solver.post(IntConstraintFactory.scalar(new IntVar[]{xb, d, xd}, new int[]{1, 1, -1}, "=", VariableFactory.fixed(1, solver)));
    }

    @Override
    public void configureSearch() {
        //solver.set(IntStrategyFactory.domOverWDeg_InDomainMin(m_allVars, 0));
        solver.set(IntStrategyFactory.ImpactBased(m_allVars, Calendar.getInstance().getTimeInMillis()));
    }

    @Override
    public void solve() {
        solver.findAllSolutions();
    }

    @Override
    public void prettyOut() {

    }

    public static void main(final String... args) {
        new IntroductiveExample().execute(args);
    }
}
