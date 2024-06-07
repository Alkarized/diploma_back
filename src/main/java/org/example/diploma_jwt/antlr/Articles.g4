grammar Articles;

ARTICLE     : [A-Za-zА-я_.0-9]+ ;
FIRM        : '('[A-Za-zА-я_.0-9]+')' ;
INNER_CODE        : '['[A-Za-zА-я_.0-9]+']' ;

PLUS         : '+' ;
MINUS        : '-' ;
MUL          : '*' ;
DIV          : '/' ;
LPAREN       : '(' ;
RPAREN       : ')' ;
WS           : [ \t\r\n]+ -> skip ; // Пропускать пробелы и другие пробельные символы

// Парсер
expr         : expr (PLUS | MINUS) expr   # AddSub
             | term                       # TermOnly
             ;

term         : term (MUL | DIV) term      # MulDiv
             | factor                     # FactorOnly
             ;

factor       : LPAREN expr RPAREN         # ParenExpr
             | article                    # ArticleExpr
             | articleWithFirm            # ArticleWithFirmExpr
             | innerCode                  # InnerCodeExpr
             ;

article      : ARTICLE ;
articleWithFirm : ARTICLE FIRM ;
innerCode    : INNER_CODE ;