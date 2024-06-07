// Generated from F:/IDE/diploma_jwt/src/main/java/org/example/diploma_jwt/antlr/Articles.g4 by ANTLR 4.13.1
package org.example.diploma_jwt.antlr.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ArticlesParser}.
 */
public interface ArticlesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ArticlesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(ArticlesParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ArticlesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(ArticlesParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TermOnly}
	 * labeled alternative in {@link ArticlesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTermOnly(ArticlesParser.TermOnlyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TermOnly}
	 * labeled alternative in {@link ArticlesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTermOnly(ArticlesParser.TermOnlyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link ArticlesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(ArticlesParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link ArticlesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(ArticlesParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FactorOnly}
	 * labeled alternative in {@link ArticlesParser#term}.
	 * @param ctx the parse tree
	 */
	void enterFactorOnly(ArticlesParser.FactorOnlyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FactorOnly}
	 * labeled alternative in {@link ArticlesParser#term}.
	 * @param ctx the parse tree
	 */
	void exitFactorOnly(ArticlesParser.FactorOnlyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(ArticlesParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(ArticlesParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArticleExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterArticleExpr(ArticlesParser.ArticleExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArticleExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitArticleExpr(ArticlesParser.ArticleExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArticleWithFirmExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterArticleWithFirmExpr(ArticlesParser.ArticleWithFirmExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArticleWithFirmExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitArticleWithFirmExpr(ArticlesParser.ArticleWithFirmExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InnerCodeExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterInnerCodeExpr(ArticlesParser.InnerCodeExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InnerCodeExpr}
	 * labeled alternative in {@link ArticlesParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitInnerCodeExpr(ArticlesParser.InnerCodeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArticlesParser#article}.
	 * @param ctx the parse tree
	 */
	void enterArticle(ArticlesParser.ArticleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArticlesParser#article}.
	 * @param ctx the parse tree
	 */
	void exitArticle(ArticlesParser.ArticleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArticlesParser#articleWithFirm}.
	 * @param ctx the parse tree
	 */
	void enterArticleWithFirm(ArticlesParser.ArticleWithFirmContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArticlesParser#articleWithFirm}.
	 * @param ctx the parse tree
	 */
	void exitArticleWithFirm(ArticlesParser.ArticleWithFirmContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArticlesParser#innerCode}.
	 * @param ctx the parse tree
	 */
	void enterInnerCode(ArticlesParser.InnerCodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArticlesParser#innerCode}.
	 * @param ctx the parse tree
	 */
	void exitInnerCode(ArticlesParser.InnerCodeContext ctx);
}