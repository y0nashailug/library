package ui.rulesets;

import librarysystem.Util;
import ui.panels.AddBookCopyPanel;
import ui.panels.AddBookPanel;

import java.awt.*;

public class AddBookCopyRuleSet implements RuleSet {
    private AddBookCopyPanel addBookCopyPanel;

    @Override
    public void applyRules(Component ob) throws RuleException {
        addBookCopyPanel = (AddBookCopyPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(addBookCopyPanel.getIsbn() == null || addBookCopyPanel.getIsbn().isEmpty())
            throw new RuleException("Isbn must be non-empty");

    }
}
