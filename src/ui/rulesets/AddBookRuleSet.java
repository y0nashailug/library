package ui.rulesets;

import ui.panels.AddBookPanel;

import java.awt.*;

public class AddBookRuleSet implements RuleSet {
    private AddBookPanel addBookPanel;

    @Override
    public void applyRules(Component ob) throws RuleException {
        addBookPanel = (AddBookPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(addBookPanel.getIsbn().isEmpty())
            throw new RuleException("Isbn must be non-empty");
    }
}
