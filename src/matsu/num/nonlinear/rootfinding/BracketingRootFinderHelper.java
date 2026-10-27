/*
 * Copyright © 2026 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.10.18
 */
package matsu.num.nonlinear.rootfinding;

import java.util.function.DoubleUnaryOperator;

/**
 * {@link BracketingRootFinder} の実装を補助するヘルパ.
 * 
 * @author Matsuura Y.
 */
final class BracketingRootFinderHelper {

    private BracketingRootFinderHelper() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@link BracketingRootFinder#solve} の引数バリデータ.
     * 
     * @param function 1変数関数
     * @param lower 探索区間の下限, 有限でなければならない
     * @param upper 探索区間の上限, 有限であり, {@code lower} 以上でなければならない
     * @param targetIntervalWidth 目標となる解の区間幅, 0以上の有限値でなければならない
     * @throws IllegalArgumentException
     *             {@code lower}, {@code upper},
     *             {@code targetIntervalWidth} が不正な場合,
     *             探索区間の端での関数値が不正な場合
     * @throws NullPointerException 引数に null を含む場合
     */
    static void validateArgsOfSolve(
            DoubleUnaryOperator function,
            double lower, double upper, double targetIntervalWidth) {

        validateSettings(lower, upper, targetIntervalWidth);

        double lowerEdgeValue = function.applyAsDouble(lower);
        double upperEdgeValue = function.applyAsDouble(upper);
        if (Double.isNaN(lowerEdgeValue) || Double.isNaN(upperEdgeValue)) {
            throw new IllegalArgumentException(
                    "Invalid edge values: (lower, upper) = (%s, %s)"
                            .formatted(lowerEdgeValue, upperEdgeValue));
        }
        if (lowerEdgeValue == 0d || upperEdgeValue == 0d) {
            return;
        }
        if ((lowerEdgeValue > 0d && upperEdgeValue > 0d)
                || (lowerEdgeValue < 0d && upperEdgeValue < 0d)) {
            throw new IllegalArgumentException(
                    "Invalid edge values: (lower, upper) = (%s, %s)"
                            .formatted(lowerEdgeValue, upperEdgeValue));
        }
    }

    /**
     * {@link BracketingRootFinder#solve} の, 設定に関わる引数バリデータ.
     * 
     * @param lower 探索区間の下限, 有限でなければならない
     * @param upper 探索区間の上限, 有限であり, {@code lower} 以上でなければならない
     * @param targetIntervalWidth 目標となる解の区間幅, 0以上の有限値でなければならない
     * @throws IllegalArgumentException
     *             {@code lower}, {@code upper},
     *             {@code targetIntervalWidth} が不正な場合
     */
    static void validateSettings(double lower, double upper, double targetIntervalWidth) {
        if (!Double.isFinite(lower)
                || !Double.isFinite(upper)
                || !(lower <= upper)) {

            throw new IllegalArgumentException("Invalid interval");
        }

        if (!Double.isFinite(targetIntervalWidth) || !(targetIntervalWidth >= 0.0)) {
            throw new IllegalArgumentException("Invalid target interval width");
        }
    }
}
