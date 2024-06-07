package org.example.diploma_jwt.antlr;

import org.example.diploma_jwt.antlr.generated.ArticlesBaseVisitor;
import org.example.diploma_jwt.antlr.generated.ArticlesParser;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.usable.ParsedExcelData;

public class ArticleEvaluator extends ArticlesBaseVisitor<Double> {
    private final ParsedExcelData data;

    public ArticleEvaluator(ParsedExcelData data) {
        this.data = data;
    }

    @Override
    public Double visitAddSub(ArticlesParser.AddSubContext ctx) {
        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));
        if (ctx.PLUS() != null) {
            return left + right;
        } else {
            return left - right;
        }
    }

    @Override
    public Double visitMulDiv(ArticlesParser.MulDivContext ctx) {
        double left = visit(ctx.term(0));
        double right = visit(ctx.term(1));
        if (ctx.MUL() != null) {
            return left * right;
        } else {
            return left / right;
        }
    }

    @Override
    public Double visitParenExpr(ArticlesParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitArticleExpr(ArticlesParser.ArticleExprContext ctx) {
        String article = ctx.article().ARTICLE().getText();
        Item item = data.getNonEmptyAndSameArticle().getOrDefault(article, null);
        return item == null ? null : item.getPrice();
    }

    @Override
    public Double visitArticleWithFirmExpr(ArticlesParser.ArticleWithFirmExprContext ctx) {
        String article = ctx.articleWithFirm().ARTICLE().getText();
        String company = ctx.articleWithFirm().FIRM().getText().strip().replace("(","").replace(")", "");
        Item item = data.getNonEmptyAndSameArticleCompany().getOrDefault(article + " " + company, null);
        return item == null ? null : item.getPrice();
    }

    @Override
    public Double visitInnerCodeExpr(ArticlesParser.InnerCodeExprContext ctx) {
        String code = ctx.innerCode().INNER_CODE().getText().replace("[", "").replace("]", "");
        Item item = data.getNonEmptyAndSameCode().getOrDefault(code, null);
        return item == null ? null : item.getPrice();
    }
}
