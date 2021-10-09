package ui.rulesets;

import ui.panels.CheckoutBook;

import java.awt.*;

public class CheckoutBookRuleSet implements RuleSet {
    private CheckoutBook checkoutBook;

    @Override
    public void applyRules(Component ob) throws RuleException {
        checkoutBook = (CheckoutBook) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(checkoutBook.getMemberId() == null || checkoutBook.getMemberId().isEmpty())
            throw new RuleException("Member id must be non-empty");

        if(checkoutBook.getIsbn() == null || checkoutBook.getIsbn().isEmpty())
            throw new RuleException("Isbn must be non-empty");
    }
}
