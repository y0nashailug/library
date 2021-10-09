package ui.rulesets;

import ui.panels.NewMemberPanel;

import java.awt.*;

public class AddMemberRuleSet implements RuleSet {
    private NewMemberPanel newMemberPanel;

    @Override
    public void applyRules(Component ob) throws RuleException {
        newMemberPanel = (NewMemberPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(newMemberPanel.getMemberId() == null || newMemberPanel.getMemberId().isEmpty())
            throw new RuleException("Member id must be non-empty");
    }
}
