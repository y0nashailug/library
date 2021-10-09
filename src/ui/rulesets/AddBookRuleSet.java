package ui.rulesets;

import librarysystem.Util;
import ui.panels.AddBookPanel;

import java.awt.*;

public class AddBookRuleSet implements RuleSet {
    private AddBookPanel addBookPanel;

    @Override
    public void applyRules(Component ob) throws RuleException {
        addBookPanel = (AddBookPanel) ob;
        nonemptyRule();
        isNumeric();
        maxCheckoutLengthValid();
    }

    private void nonemptyRule() throws RuleException {
        if(addBookPanel.getIsbn() == null || addBookPanel.getIsbn().isEmpty())
            throw new RuleException("Isbn must be non-empty");

        if(addBookPanel.getMaxCheckoutLength() == null || addBookPanel.getMaxCheckoutLength().isEmpty())
            throw new RuleException("Max checkout length must be non-empty");
    }

    private void isNumeric() throws RuleException {
        if(!Util.isNumeric(addBookPanel.getMaxCheckoutLength()))
            throw new RuleException("Max checkout length must be numeric");
    }

    private void maxCheckoutLengthValid() throws RuleException {
        if(Integer.parseInt(addBookPanel.getMaxCheckoutLength()) != 7 || Integer.parseInt(addBookPanel.getMaxCheckoutLength()) != 21)
            throw new RuleException("Max checkout length must be 7 or 21");
    }
}
