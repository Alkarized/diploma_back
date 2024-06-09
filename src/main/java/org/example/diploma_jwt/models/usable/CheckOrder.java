package org.example.diploma_jwt.models.usable;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.diploma_jwt.antlr.ArticleEvaluator;
import org.example.diploma_jwt.antlr.generated.ArticlesLexer;
import org.example.diploma_jwt.antlr.generated.ArticlesParser;
import org.example.diploma_jwt.interfaces.ItemComparable;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.usable.item_data.ArticleAndCompanyForm;
import org.example.diploma_jwt.models.usable.item_data.ManyItemsForm;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public enum CheckOrder implements ItemComparable {
    ONLY_ARTICLE {
        @Override
        public boolean compareItems(Item item, ParsedExcelData data, Settings settings) {
            String article = item.getComplexItem().getArticleForm();
            if (article != null){
                //log.info("only artilce search");
                //log.info("oas keyset: {}", data.getNonEmptyAndSameArticle().keySet());
                Item dataItem = data.getNonEmptyAndSameArticle().getOrDefault(article, null);
                if (dataItem != null){
                    //log.info("oas found dataItem: {}", dataItem);
                    if (!settings.getBlackListForm().getArticles().contains(article)){
                        //log.info("oas new price");
                        item.setPrice(dataItem.getPrice());
                        return true;
                    }
                }
            }
            return false;
        }
    }, ARTICLE_COMPANY {
        @Override
        public boolean compareItems(Item item, ParsedExcelData data, Settings settings) {

            //log.info("ARTC_COMP ITEM: {}", item);

            ArticleAndCompanyForm articleAndCompany = item.getComplexItem().getArticleAndCompanyForm();
            //log.info("ARTC_COMP ITEM2: {}", articleAndCompany);
            //log.info("ARTC_COMP ITEM3: {}", item.getComplexItem());
            if (articleAndCompany != null){
                //log.info("artilce and comp search");
                Item dataItem = data.getNonEmptyAndSameArticleCompany().getOrDefault(articleAndCompany.getArticle() + " " + articleAndCompany.getCompany(), null);
                if (dataItem != null){
                    if (!settings.getBlackListForm().getArticleAndCompany().contains(articleAndCompany)){
                        //log.info("ancs new price");
                        item.setPrice(dataItem.getPrice());
                        return true;
                    }
                }
            }
            return false;
        }
    }, MANY_ARTICLES {
        @Override
        public boolean compareItems(Item item, ParsedExcelData data, Settings settings) {
            ManyItemsForm manyItems = item.getComplexItem().getManyItemsForm();
            List<Double> prices = new ArrayList<>();
            Item dataItem;
            //log.info("many articles search: {}", manyItems);
            //log.info("data article: {}", data.getNonEmptyAndSameArticle());
            //log.info("data article and company: {}", data.getNonEmptyAndSameArticleCompany());
            //log.info("data codes: {}", data.getNonEmptyAndSameCode());
            if (manyItems == null){
                return false;
            }
            for (String article: manyItems.getArticles()){
                dataItem = data.getNonEmptyAndSameArticle().getOrDefault(article, null);
                if (dataItem == null && settings.getFailOnNoneFound()){
                    return false;
                }
                if (dataItem != null && !settings.getBlackListForm().getArticles().contains(article)){
                    prices.add(dataItem.getPrice());
                    //log.info("added article");
                }
            }

            for (String code: manyItems.getCodes()){
                dataItem = data.getNonEmptyAndSameCode().getOrDefault(code, null);
                if (dataItem == null && settings.getFailOnNoneFound()){
                    return false;
                }
                if (dataItem != null && !settings.getBlackListForm().getCodes().contains(code)){
                    prices.add(dataItem.getPrice());
                    log.info("added code");
                }
            }

            for (ArticleAndCompanyForm articleAndCompany: manyItems.getArticleAndCompany()){
                //log.info("testtt: {}", articleAndCompany.getArticle() + " " + articleAndCompany.getCompany());
                //log.info("article and cmp : {}   \n and {}", data.nonEmptyAndSameArticleCompany.keySet(), data.nonEmptyAndSameArticleCompany.containsKey(articleAndCompany.getArticle() + " " + articleAndCompany.getCompany()));
                dataItem = data.getNonEmptyAndSameArticleCompany().getOrDefault(articleAndCompany.getArticle() + " " + articleAndCompany.getCompany(), null);
                if (dataItem == null && settings.getFailOnNoneFound()){
                    return false;
                }
                if (dataItem != null && !settings.getBlackListForm().getArticleAndCompany().contains(articleAndCompany)){
                    prices.add(dataItem.getPrice());
                    //log.info("added article and company");
                }
            }

            //log.info("get new prices: {}", prices);

            if (manyItems.getType() == null){
                item.setPrice(settings.getManyType().getManyItemsPrice(prices));
            } else {
                item.setPrice(manyItems.getType().getManyItemsPrice(prices));
            }

            return true;
        }
    }, ONLY_CODE {
        @Override
        public boolean compareItems(Item item, ParsedExcelData data, Settings settings) {
            String code = item.getComplexItem().getCodeForm();
            if (code != null){
                //log.info("code search");

                Item dataItem = data.getNonEmptyAndSameCode().getOrDefault(code, null);
                if (dataItem != null){
                    if (!settings.getBlackListForm().getCodes().contains(code)){
                        //log.info("cs new price");
                        item.setPrice(dataItem.getPrice());
                        return true;
                    }
                }
            }
            return false;
        }
    }, COMPLEX {
        @Override
        public boolean compareItems(Item item, ParsedExcelData data, Settings settings) {
            //log.info("complex search");
            String complex = item.getComplexItem().getComplexForm();
            if (complex != null){
                //log.info("complex 1");
                CharStream input = CharStreams.fromString(complex);
                ArticlesLexer lexer = new ArticlesLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                ArticlesParser parser = new ArticlesParser(tokens);
                //log.info("complex 2");
                ParseTree tree = parser.expr();

                ArticleEvaluator evaluator = new ArticleEvaluator(data);
                try {
                    //log.info("complex 3");
                    Double result = evaluator.visit(tree);
                    //log.info("complex 4");
                    item.setPrice(result);
                    //log.info("complex result : {}", result);
                    //log.info("complex 6");
                    //log.info("complex new price");
                    item.setPrice(result);
                    //log.info("complex 7");
                    return true;
                } catch (NullPointerException e){
                    //log.info("complex 5");
                   return false;
                }
            }
            return false;

        }
    }, ARTICLE_FIELD {
        @Override
        public boolean compareItems(Item item, ParsedExcelData data, Settings settings) {
            String article = item.getComplexItem().getArticleFieldForm();
            if (article != null){
                //log.info("artilce field search");
                Item dataItem = data.getNonEmptyAndSameArticle().getOrDefault(article, null);
                if (dataItem != null){
                    //log.info("afs data found: {}", dataItem);
                    if (!settings.getBlackListForm().getArticles().contains(article)){
                        //log.info("afs new price");
                        item.setPrice(dataItem.getPrice());
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
