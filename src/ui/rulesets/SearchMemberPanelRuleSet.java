package ui.rulesets;

import ui.panels.SearchMemberForCheckoutPanel;
import ui.panels.SearchMemberPanel;

import java.awt.*;

public class SearchMemberPanelRuleSet implements RuleSet {
    private SearchMemberPanel searchMemberPanel;
    @Override
    public void applyRules(Component ob) throws RuleException {
        searchMemberPanel = (SearchMemberPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(searchMemberPanel.getMemberId() == null || searchMemberPanel.getMemberId().isEmpty())
            throw new RuleException("Member id must be non-empty");
    }
}
