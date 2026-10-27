/*
 * Copyright © 2026 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.nonlinear.rootfinding;

import static matsu.num.nonlinear.rootfinding.BracketingRootFinderHelper.*;

import java.util.function.DoubleUnaryOperator;

import org.junit.Test;
import org.junit.Test.None;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link BracketingRootFinderHelper} のテスト.
 */
@RunWith(Enclosed.class)
final class BracketingRootFinderHelperTest {

    public static class 引数バリデーションメソッドのテスト {

        @Test
        public void test_区間のテスト_IAEx例外パターン() {

            double[][] luPairs = {
                    { 3, 2 },
                    { 0, Double.POSITIVE_INFINITY },
                    { Double.NEGATIVE_INFINITY, 0 },
                    { 0, Double.NaN },
                    { Double.NaN, 0 }
            };

            for (double[] luPair : luPairs) {
                try {
                    // 例外がスローされたら正常
                    validateSettings(luPair[0], luPair[1], 1d);
                    throw new AssertionError(
                            "did not throw IllegalArgumentException: "
                                    + "(" + luPair[0] + ", " + luPair[1] + ")");
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        @Test(expected = None.class)
        public void test_区間のテスト_正常パターン() {

            double[][] luPairs = {
                    { 2, 3 },
                    { 2, 2 }
            };

            for (double[] luPair : luPairs) {
                // 例外がスローされなければ正常
                validateSettings(luPair[0], luPair[1], 1d);
            }
        }

        @Test
        public void test_targetIntervalWidth_IAEx例外パターン() {

            double[] widths = {
                    -1d,
                    Double.POSITIVE_INFINITY,
                    Double.NaN
            };

            for (double targetIntervalWidth : widths) {
                try {
                    // 例外がスローされたら正常
                    validateSettings(0d, 1d, targetIntervalWidth);
                    throw new AssertionError(
                            "did not throw IllegalArgumentException: "
                                    + targetIntervalWidth);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        @Test(expected = None.class)
        public void test_targetIntervalWidth_正常パターン() {

            double[] widths = {
                    1d,
                    0d
            };

            for (double targetIntervalWidth : widths) {
                // 例外がスローされなければ正常
                validateSettings(0d, 1d, targetIntervalWidth);
            }
        }

        @Test
        public void test_関数値_IAEx例外パターン() {

            double lower = 1d;
            double upper = 2d;

            DoubleUnaryOperator[] funcs = {
                    x -> 2 * x,
                    x -> -2 * x,
                    x -> Double.NaN
            };

            for (DoubleUnaryOperator func : funcs) {
                try {
                    // 例外がスローされたら正常
                    validateArgsOfSolve(func, lower, upper, 1d);
                    throw new AssertionError(
                            "did not throw IllegalArgumentException: "
                                    + "(" + func.applyAsDouble(lower) + ", " + func.applyAsDouble(upper) + ")");
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        @Test(expected = None.class)
        public void test_関数値_正常パターン() {

            double lower = 1d;
            double upper = 2d;

            DoubleUnaryOperator[] funcs = {
                    x -> 2 * x - 3,
                    x -> 2 * x - 2,
                    x -> -2 * x + 3,
                    x -> -2 * x + 4,
                    x -> (2 * x - 3) * Double.POSITIVE_INFINITY
            };

            for (DoubleUnaryOperator func : funcs) {
                // 例外がスローされなければ正常
                validateArgsOfSolve(func, lower, upper, 1d);
            }
        }
    }
}
