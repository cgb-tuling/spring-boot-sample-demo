package org.example.spel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author wurenhai
 * @since 2019/1/11 9:44
 */
public class AspectExpressContext {

    private final EvaluationContext context;
    private final ExpressionParser parser;
    private final ParserContext parserContext;

    private AspectExpressContext() {
        this(null);
    }

    private AspectExpressContext(ApplicationContext applicationContext) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (applicationContext != null) {
            context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        }
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);
        this.context = context;
        this.parser = new SpelExpressionParser(config);
        this.parserContext = new TemplateParserContext();
    }

    public AspectExpressContext(Object target, Object[] args, Object result) {
        this();
        context.setVariable("target", target);
        context.setVariable("result", result);
        for (int i = 0; i < args.length; i++) {
            context.setVariable("a" + i, args[i]);
        }
    }

    public String getValue(String express) {
        Expression expression = parser.parseExpression(express, parserContext);
        return expression.getValue(context, String.class);
    }

}