package ui.rulesets;

import java.awt.*;

public interface RuleSet {
    void applyRules(Component ob) throws RuleException;
}
