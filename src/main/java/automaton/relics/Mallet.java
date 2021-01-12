package automaton.relics;

import automaton.AutomatonMod;
import automaton.cards.FineTuning;
import automaton.cards.FunctionCard;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class Mallet extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("Mallet");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Mallet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Mallet.png"));
    public boolean firstTurn = false;

    public Mallet() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atPreBattle() {
        firstTurn = true;
    }

    @Override
    public void atTurnStart() {
        if (firstTurn) {
            flash();
            AbstractCard c = new FineTuning();
            c.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
            this.firstTurn = false;
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}