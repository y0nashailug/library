package ui.rulesets;

import ui.panels.*;

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
        map.put(CheckoutStatus.class, new CheckoutStatusRuleSet());
        map.put(SearchMemberForCheckoutPanel.class, new SearchMemberForCheckoutPanelRuleSet());
        map.put(SearchBookPanel.class, new SearchBookPanelRuleSet());
        map.put(SearchMemberPanel.class, new SearchMemberPanelRuleSet());
    }
    public static RuleSet getRuleSet(Component c) {
        Class<? extends Component> cl = c.getClass();
        if(!map.containsKey(cl)) {
            throw new IllegalArgumentException("No RuleSet found for this Component");
        }
        return map.get(cl);
    }
}
