package ui.rulesets;

import ui.panels.CheckoutStatus;

import java.awt.*;

public class CheckoutStatusRuleSet implements RuleSet {
    private CheckoutStatus checkoutStatus;

    @Override
    public void applyRules(Component ob) throws RuleException {
        checkoutStatus = (CheckoutStatus) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(checkoutStatus.getIsbn() == null || checkoutStatus.getIsbn().isEmpty())
            throw new RuleException("Isbn must be non-empty");
    }
}
