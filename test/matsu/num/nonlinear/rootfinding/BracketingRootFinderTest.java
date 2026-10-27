/*
 * Copyright © 2026 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.nonlinear.rootfinding;

import java.util.Collection;
import java.util.function.DoubleUnaryOperator;

import org.junit.Ignore;
import org.junit.Test;

/**
 * {@link BracketingRootFinder} の実装のテストのための抽象クラスを提供する.
 * 
 * <p>
 * このトップレベルのクラスはインスタンス化不可能である. <br>
 * メンバークラスを継承し, 必要なメソッドをオーバーライドすることで, JUnit 自動テストが実行される.
 * </p>
 * 
 * @author Matsuura Y.
 */
final class BracketingRootFinderTest {

    private BracketingRootFinderTest() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@link BracketingRootFinder#solve} のテストを行う.
     * 
     * <p>
     * 継承をして抽象メソッド {@link #fixtures()} をオーバーライドすればよい.
     * </p>
     */
    @Ignore
    static abstract class RootFinding {

        /** 唯一のコンストラクタ. */
        RootFinding() {
            super();
        }

        @Test
        public void test_Fixtureに対して検証を実行する() {
            BracketingRootFinder rootFinder = rootFinder();
            for (Fixture f : fixtures()) {
                try {
                    DoubleUnaryOperator func = f.func();
                    double lower = f.lower();
                    double upper = f.upper();
                    double root = rootFinder.solve(func, lower, upper, 0d);

                    double lowerValue = func.applyAsDouble(lower);
                    double upperValue = func.applyAsDouble(upper);
                    double scale = Math.abs(lowerValue) + Math.abs(upperValue);
                    double rootValue = func.applyAsDouble(root);
                    double epsR = 1E-10;

                    if (Math.abs(rootValue) > epsR * (1d + scale)) {
                        throw new AssertionError(
                                root + "is not root: case " + f);
                    }
                } catch (Exception e) {
                    throw new AssertionError("exception is thrown: case " + f, e);
                }
            }
        }

        /** テストで使う Fixture の集合を返す. */
        abstract Collection<Fixture> fixtures();

        /** テストする {@link BracketingRootFinder} を返す. */
        abstract BracketingRootFinder rootFinder();
    }

    /**
     * {@link RootFinding} クラスの Fixture. <br>
     * 1個のユースケースを扱う.
     * 
     * <p>
     * 求根の対象関数と, 初期区間 (下限, 上限) から構成される. <br>
     * 求められた解の周辺の関数値を評価し, 求められた値がおおよそ正しいかを検出する. <br>
     * 検出の安定性のために, {@code func} の数値オーダーが 1 であることが望ましい. <br>
     * {@code funcStr} は関数の文字列表現である.
     * </p>
     */
    static record Fixture(
            DoubleUnaryOperator func, String funcStr, double lower, double upper) {

        /** 唯一のコンストラクタ, 引数チェックは行わない. */
        Fixture {
        }

        @Override
        public final String toString() {
            return "[func=%s, lower=%s, upper=%s]"
                    .formatted(funcStr, lower, upper);
        }
    }
}
