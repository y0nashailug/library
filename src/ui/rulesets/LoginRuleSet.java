package ui.rulesets;

import ui.panels.LoginPanel;

import java.awt.*;

public class LoginRuleSet implements RuleSet {
    private LoginPanel loginPanel;

    @Override
    public void applyRules(Component ob) throws RuleException {
        loginPanel = (LoginPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(loginPanel.getPassword().length() == 0 || loginPanel.getUserName().isEmpty())
            throw new RuleException("All fields must be non-empty");
    }
}
