package ui.rulesets;

import ui.panels.SearchMemberForCheckoutPanel;

import java.awt.*;

public class SearchMemberForCheckoutPanelRuleSet implements RuleSet {
    private SearchMemberForCheckoutPanel searchMemberForCheckoutPanel;
    @Override
    public void applyRules(Component ob) throws RuleException {
        searchMemberForCheckoutPanel = (SearchMemberForCheckoutPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(searchMemberForCheckoutPanel.getMemberId() == null || searchMemberForCheckoutPanel.getMemberId().isEmpty())
            throw new RuleException("Member id must be non-empty");
    }
}
