package org.example.diploma_jwt.mains;

public class Main1 {
//    final static String ARTICLE_REGEX = "(?m)^(Артикул: *(\\[(?<code>[\\wА-я-. ]+[\\wА-я-.])]|(?<article>[\\wА-я-. ]+[\\wА-я-.])( *\\((?<firm>[\\wА-я-. ]+[\\wА-я-.])\\))?) *(\\| *(?<comment>.*))?)$";
//    final static String FORMULA_REGEX = "(?m)^(Формула: ?(?<formula>.+))$";
//    final static String MANY_ARTICLES_REGEX = "(?m)^(Артикулы: *(\\((?<type>min|max|mean|sum)\\) *)?[\r\n\f](?<articles>[\\S\\s]+?);)";
//
//    final static Pattern ARTICLE_PATTERN = Pattern.compile(ARTICLE_REGEX);
//    final static Pattern FORMULA_PATTERN = Pattern.compile(FORMULA_REGEX);
//    final static Pattern MANY_ARTICLES_PATTERN = Pattern.compile(MANY_ARTICLES_REGEX);

    public static void main(String[] args) {

/*
        String expression = "a1 + [c1] + a2 (c12)"; // Пример выражения

        HashMap<String, Item> t1 = new HashMap<>();
        HashMap<String, Item> t2 = new HashMap<>();
        HashMap<String, Item> t3 = new HashMap<>();

        Item i1 = new Item();
        i1.setPrice(1d);
        t1.put("a1", i1);

        Item i2 = new Item();
        i2.setPrice(10d);
        t2.put("c1", i2);

        Item i3 = new Item();
        i3.setPrice(100d);
        t3.put("a2 c1", i3);

        // Создаем словарь значений
        ParsedExcelData parsedExcelData = new ParsedExcelData();
        parsedExcelData.setNonEmptyAndSameArticle(t1);
        parsedExcelData.setNonEmptyAndSameCode(t2);
        parsedExcelData.setNonEmptyAndSameArticleCompany(t3);

        CharStream input = CharStreams.fromString(expression);
        //sout();
        ArticlesLexer lexer = new ArticlesLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArticlesParser parser = new ArticlesParser(tokens);

        ParseTree tree = parser.expr();

        ArticleEvaluator evaluator = new ArticleEvaluator(parsedExcelData);
        try {
            Double result = evaluator.visit(tree);
            System.out.println("Result: " + result);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
*/


//        String input1 = "Артикул: ABC123\n" +
//                "Артикул: DEF456 XYZ\n" +
//                "Артикул: 789\n" +
//                "Артикул: GHI789 | Comment\n" +
//                "Артикул: JKL012 MNO | Another Comment\n" +
//                "Артикул: 345 | Some Comment\n" +
//                "\n" +
//                "Артикулы:\n" +
//                "ABC123\n" +
//                "456\n" +
//                "DEF456 XYZ | A Comment\n" +
//                "\n" +
//                "Сложная формула: a^2 + b^2 = c^2";
//
//        String input = "Артикулы:\n" +
//                "Тест1 \n" +
//                "[Тест2]\n" +
//                "Тест3 (Фирма) | Ком11;";
//
//        ArticleAndCompanyForm articleAndCompanyForm = new ArticleAndCompanyForm("t1", "t2");
//        List<ArticleAndCompanyForm> articleAndCompanyFormList = new ArrayList<>();
//        articleAndCompanyFormList.add(new ArticleAndCompanyForm("t1", "t2"));
//
//        if (articleAndCompanyFormList.contains(articleAndCompanyForm)) {
//            System.out.println("YES");
//        }

        //@Async todo

//        final Matcher articleMatcher = ARTICLE_PATTERN.matcher(input);
//        final Matcher formulaMatcher = FORMULA_PATTERN.matcher(input);
//        final Matcher manyArticlesMatcher = MANY_ARTICLES_PATTERN.matcher(input);
//
//        ComplexItem complexItem = new ComplexItem();
//
//        if (articleMatcher.find()) {
//            if (articleMatcher.group("firm") != null) {
//                complexItem.setArticleAndCompanyForm(new ArticleAndCompanyForm(articleMatcher.group("article"),  articleMatcher.group("firm")));
//            } else if (articleMatcher.group("code") != null) {
//                complexItem.setCodeForm(articleMatcher.group("code"));
//            } else {
//                complexItem.setArticleForm(articleMatcher.group("article"));
//            }
//        }
//
//        if (formulaMatcher.find()){
//            if (formulaMatcher.group("formula") != null) {
//                complexItem.setComplexForm(formulaMatcher.group("formula"));
//            }
//        }
//
//        if (manyArticlesMatcher.find()){
//            if (manyArticlesMatcher.group("articles") != null) {
//                complexItem.setManyItemsForm(BlackListParser.parseList(manyArticlesMatcher.group("articles")));
//            }
//        }
    }
}
