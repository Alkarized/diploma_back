// Generated from F:/IDE/diploma_jwt/src/main/java/org/example/diploma_jwt/antlr/Articles.g4 by ANTLR 4.13.1
package org.example.diploma_jwt.antlr.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ArticlesParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ArticlesVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ArticlesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(ArticlesParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TermOnly}
	 * labeled alternative in {@link ArticlesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermOnly(ArticlesParser.TermOnlyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link ArticlesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(ArticlesParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FactorOnly}
	 * labeled alternative in {@link ArticlesParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactorOnly(ArticlesParser.FactorOnlyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(ArticlesParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArticleExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArticleExpr(ArticlesParser.ArticleExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArticleWithFirmExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArticleWithFirmExpr(ArticlesParser.ArticleWithFirmExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InnerCodeExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInnerCodeExpr(ArticlesParser.InnerCodeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ArticlesParser#article}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArticle(ArticlesParser.ArticleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ArticlesParser#articleWithFirm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArticleWithFirm(ArticlesParser.ArticleWithFirmContext ctx);
	/**
	 * Visit a parse tree produced by {@link ArticlesParser#innerCode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInnerCode(ArticlesParser.InnerCodeContext ctx);
}