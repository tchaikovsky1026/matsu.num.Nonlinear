/*
 * Copyright © 2026 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.nonlinear.rootfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.nonlinear.rootfinding.BracketingRootFinderTest.Fixture;

/**
 * {@link BisectionRootFinder} のテスト.
 */
@RunWith(Enclosed.class)
final class BisectionRootFinderTest {

    public static final class RootFinding extends BracketingRootFinderTest.RootFinding {

        @Override
        Collection<Fixture> fixtures() {
            List<Fixture> list = new ArrayList<>();
            list.addAll(increasingFuncFixtures());
            list.addAll(decreasingFuncFixtures());

            return list;
        }

        /** 増加関数でのパターン. */
        private List<Fixture> increasingFuncFixtures() {
            List<Fixture> list = new ArrayList<>();

            DoubleUnaryOperator func = x -> 2 * x - 1;
            String funcStr = "2x-1";

            // 通常のパターン
            list.add(new Fixture(func, funcStr, 0, 2));
            // 上端が解
            list.add(new Fixture(func, funcStr, 0, 0.5));
            // 下端が解
            list.add(new Fixture(func, funcStr, 0.5, 2));
            // 狭区間
            list.add(new Fixture(func, funcStr, 0.5 - 1E-15, 0.5 + 1E-15));

            return list;
        }

        /** 減少関数でのパターン. */
        private List<Fixture> decreasingFuncFixtures() {
            List<Fixture> list = new ArrayList<>();

            DoubleUnaryOperator func = x -> -2 * x - 1;
            String funcStr = "-2x-1";

            // 通常のパターン
            list.add(new Fixture(func, funcStr, -1, 0));
            // 上端が解
            list.add(new Fixture(func, funcStr, -1, -0.5));
            // 下端が解
            list.add(new Fixture(func, funcStr, -0.5, 0));
            // 狭区間
            list.add(new Fixture(func, funcStr, -0.5 - 1E-15, -0.5 + 1E-15));

            return list;
        }

        @Override
        BracketingRootFinder rootFinder() {
            return BisectionRootFinder.instance();
        }
    }
}
