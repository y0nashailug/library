package ui.rulesets;

import ui.panels.AddBookPanel;
import ui.panels.CheckoutBook;
import ui.panels.LoginPanel;
import ui.panels.NewMemberPanel;

import java.awt.*;
import java.util.HashMap;

final public class RuleSetFactory {
    private RuleSetFactory() {}
    static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
    static {
        map.put(LoginPanel.class, new LoginRuleSet());
        map.put(AddBookPanel.class, new AddBookRuleSet());
        map.put(NewMemberPanel.class, new AddMemberRuleSet());
        map.put(CheckoutBook.class, new CheckoutBookRuleSet());
    }
    public static RuleSet getRuleSet(Component c) {
        Class<? extends Component> cl = c.getClass();
        if(!map.containsKey(cl)) {
            throw new IllegalArgumentException("No RuleSet found for this Component");
        }
        return map.get(cl);
    }
}
