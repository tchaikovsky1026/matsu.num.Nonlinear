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
 * ブラケット法による1変数関数の求根 (零点の算出) を行うインターフェース. <br>
 * ブラケット法とは, 1変数連続関数を考え, 区間端点で関数が異符号となるような探索区間を与え,
 * 区間を狭めるように探索することで零点を見つけるアルゴリズムである.
 * 
 * @author Matsuura Y.
 * 
 * @implSpec
 *               このインターフェースは主に, 戻り値型を公開するために用意されており,
 *               モジュール外での実装は想定されていない. <br>
 *               予告されないメソッド追加等により, コンパイルエラーを引き起こす可能性がある.
 */
public interface BracketingRootFinder {

    /**
     * ブラケット法により, 与えられた1変数関数の零点を求める.
     * 
     * <p>
     * 1変数関数, 探索区間の下限と上限, 目標となる解の区間幅 (零点の精度に相当) を与える. <br>
     * 関数が不連続な場合でも, 連続として扱う. <br>
     * 戻り値は, 必ず探索区間下限と上限の間の値となる. <br>
     * ただし, 区間内に関数の NaN を含む場合, 結果は保証されない.
     * </p>
     * 
     * <p>
     * 索区間の端における関数の値がブラケット法の前提条件を満たさなければならない. <br>
     * すなわち, 端点で同符号, あるいは NaN となってはいけない. <br>
     * 端点で inf, 0 は許容される.
     * </p>
     * 
     * @param function 1変数関数
     * @param lower 探索区間の下限, 有限でなければならない
     * @param upper 探索区間の上限, 有限であり, {@code lower} 以上でなければならない
     * @param targetIntervalWidth 目標となる解の区間幅, 0以上の有限値でなければならない
     * @return 零点
     * @throws IllegalArgumentException
     *             {@code lower}, {@code upper},
     *             {@code targetIntervalWidth} が不正な場合,
     *             探索区間の端での関数値が不正な場合
     * @throws NullPointerException 引数に null を含む場合
     */
    public abstract double solve(
            DoubleUnaryOperator function,
            double lower, double upper, double targetIntervalWidth);

}
