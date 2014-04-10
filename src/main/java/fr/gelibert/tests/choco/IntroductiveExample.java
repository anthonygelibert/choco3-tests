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
    IntVar[] all = new IntVar[5];

    @Override
    public void createSolver() {
        solver = new Solver("Introductive Example");
    }

    @Override
    public void buildModel() {
        // Variables
        all[0] = VariableFactory.enumerated("Xa", new int[]{1, 2}, solver);/* NON-NLS */
        all[1] = VariableFactory.enumerated("Xb", new int[]{1, 2, 3, 4, 5}, solver);/* NON-NLS */
        all[2] = VariableFactory.enumerated("Xc", new int[]{1, 2, 3}, solver);/* NON-NLS */
        all[3] = VariableFactory.enumerated("Xd", new int[]{1, 2, 3, 4, 5}, solver);/* NON-NLS */
        all[4] = VariableFactory.enumerated("d", new int[]{1, 2}, solver);/* NON-NLS */


        // Constraints
        solver.post(IntConstraintFactory.alldifferent(new IntVar[]{all[0], all[1], all[2], all[3]}, "BC"));
        solver.post(IntConstraintFactory.arithm(all[2], "<", all[3]));
        solver.post(IntConstraintFactory.scalar(new IntVar[]{all[1], all[4], all[3]}, new int[]{1, 1, -1}, "=", VariableFactory.fixed(1, solver)));
    }

    @Override
    public void configureSearch() {
        //solver.set(IntStrategyFactory.domOverWDeg_InDomainMin(all, 0));
        solver.set(IntStrategyFactory.ImpactBased(all, Calendar.getInstance().getTimeInMillis()));
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
