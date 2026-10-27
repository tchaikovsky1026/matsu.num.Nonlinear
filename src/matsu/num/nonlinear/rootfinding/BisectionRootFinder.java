/*
 * Copyright © 2026 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.10.27
 */
package matsu.num.nonlinear.rootfinding;

import static java.lang.Math.*;
import static matsu.num.nonlinear.rootfinding.BracketingRootFinderHelper.*;

import java.util.function.DoubleUnaryOperator;

/**
 * 二分法により実装された {@link BracketingRootFinder}.
 * 
 * <p>
 * このクラスのインスタンスは, {@link #instance()} メソッドにより得られる. .
 * </p>
 * 
 * @author Matsuura Y.
 */
public final class BisectionRootFinder implements BracketingRootFinder {

    /** このクラスの唯一のシングルトンインスタンス. */
    private static final BisectionRootFinder INSTANCE = new BisectionRootFinder();

    /**
     * このクラスのインスタンスを返す.
     * 
     * @return インスタンス
     */
    public static BisectionRootFinder instance() {
        return INSTANCE;
    }

    /** 非公開のコンストラクタ. */
    private BisectionRootFinder() {
        super();
    }

    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public double solve(
            DoubleUnaryOperator function,
            double lower, double upper, double targetIntervalWidth) {

        validateArgsOfSolve(function, lower, upper, targetIntervalWidth);

        // targetWidth は丸めの影響が無い値に修正する
        double modifiedTargetWidth = max(
                targetIntervalWidth,
                1E-15 * max(abs(lower), abs(upper)));

        // 二分法の実行
        double lowerValue = function.applyAsDouble(lower);
        double upperValue = function.applyAsDouble(upper);
        if (lowerValue == 0d) {
            return lower;
        }
        if (upperValue == 0d) {
            return upper;
        }

        int count = 0;
        while (upper - lower > modifiedTargetWidth) {
            double mid = lower + (upper - lower) * 0.5;
            double midValue = function.applyAsDouble(mid);
            if (midValue == 0d) {
                return mid;
            }
            if (Double.isNaN(midValue)) {
                return Double.NaN;
            }
            if ((lowerValue < 0 && midValue < 0)
                    || (lowerValue > 0 && midValue > 0)) {
                lower = mid;
                lowerValue = midValue;
            } else {
                upper = mid;
                upperValue = midValue;
            }

            // 無限ループの検出用
            if (count > 10000) {
                throw new AssertionError("(Bug) loop did not stop");
            }
            count++;
        }

        return lower;
    }
}
