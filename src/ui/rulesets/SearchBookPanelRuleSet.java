package ui.rulesets;

import ui.panels.SearchBookPanel;

import java.awt.*;

public class SearchBookPanelRuleSet implements RuleSet {
    private SearchBookPanel searchBookPanel;
    @Override
    public void applyRules(Component ob) throws RuleException {
        searchBookPanel = (SearchBookPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(searchBookPanel.getIsbn() == null || searchBookPanel.getIsbn().isEmpty())
            throw new RuleException("Isbn must be non-empty");
    }
}
