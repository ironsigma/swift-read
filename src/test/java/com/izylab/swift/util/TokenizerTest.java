package com.izylab.swift.util;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class TokenizerTest {
    
    private List<String> data = new ArrayList<String>();

    @Test
    public void testEmpty() {
        Tokenizer.tokenize(data, "");
        assertThat(data.size(), equalTo(0));

        Tokenizer.tokenize(data, " ");
        assertThat(data.size(), equalTo(0));

        Tokenizer.tokenize(data, "       ");
        assertThat(data.size(), equalTo(0));

        Tokenizer.tokenize(data, "\t");
        assertThat(data.size(), equalTo(0));

        Tokenizer.tokenize(data, "  \t");
        assertThat(data.size(), equalTo(0));
    }

    @Test
    public void testSplits() {
        Tokenizer.tokenize(data, "one--two");
        assertThat(data.size(), equalTo(3));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("--"));
        assertThat(data.get(2), equalTo("two"));

        Tokenizer.tokenize(data, "one -- two");
        assertThat(data.size(), equalTo(3));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("--"));
        assertThat(data.get(2), equalTo("two"));

        Tokenizer.tokenize(data, "one--two--three");
        assertThat(data.size(), equalTo(5));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("--"));
        assertThat(data.get(2), equalTo("two"));
        assertThat(data.get(3), equalTo("--"));
        assertThat(data.get(4), equalTo("three"));

        Tokenizer.tokenize(data, "one -- two -- three");
        assertThat(data.size(), equalTo(5));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("--"));
        assertThat(data.get(2), equalTo("two"));
        assertThat(data.get(3), equalTo("--"));
        assertThat(data.get(4), equalTo("three"));

        Tokenizer.tokenize(data, "one--two--three--four");
        assertThat(data.size(), equalTo(7));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("--"));
        assertThat(data.get(2), equalTo("two"));
        assertThat(data.get(3), equalTo("--"));
        assertThat(data.get(4), equalTo("three"));
        assertThat(data.get(5), equalTo("--"));
        assertThat(data.get(6), equalTo("four"));

        Tokenizer.tokenize(data, "one -- two -- three -- four");
        assertThat(data.size(), equalTo(7));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("--"));
        assertThat(data.get(2), equalTo("two"));
        assertThat(data.get(3), equalTo("--"));
        assertThat(data.get(4), equalTo("three"));
        assertThat(data.get(5), equalTo("--"));
        assertThat(data.get(6), equalTo("four"));
    }

    @Test
    public void testSpaces() {
        Tokenizer.tokenize(data, "one");
        assertThat(data.size(), equalTo(1));
        assertThat(data.get(0), equalTo("one"));

        Tokenizer.tokenize(data, "   one  ");
        assertThat(data.size(), equalTo(1));
        assertThat(data.get(0), equalTo("one"));

        Tokenizer.tokenize(data, " \t one \t  ");
        assertThat(data.size(), equalTo(1));
        assertThat(data.get(0), equalTo("one"));

        Tokenizer.tokenize(data, "one two");
        assertThat(data.size(), equalTo(2));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("two"));

        Tokenizer.tokenize(data, "one two three");
        assertThat(data.size(), equalTo(3));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("two"));
        assertThat(data.get(2), equalTo("three"));

        Tokenizer.tokenize(data, "one\ttwo");
        assertThat(data.size(), equalTo(2));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("two"));

        Tokenizer.tokenize(data, " \t one\ttwo\t  ");
        assertThat(data.size(), equalTo(2));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("two"));

        Tokenizer.tokenize(data, " \t one\ttwo\t  three");
        assertThat(data.size(), equalTo(3));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("two"));
        assertThat(data.get(2), equalTo("three"));

        Tokenizer.tokenize(data, " \t one\ttwo\t  three\t     ");
        assertThat(data.size(), equalTo(3));
        assertThat(data.get(0), equalTo("one"));
        assertThat(data.get(1), equalTo("two"));
        assertThat(data.get(2), equalTo("three"));
    }
    
    @Test
    public void testPoem() {
        String poem = "How do I love thee?\n"
                + "by Elizabeth Barrett Browning\n"
                + "How do I love thee? Let me count the ways.\n"
                + "I love thee to the depth and breadth and height\n"
                + "My soul can reach, when feeling out of sight\n"
                + "For the ends of Being and ideal Grace.\n"
                + "I love thee to the level of every day's\n"
                + "Most quiet need, by sun and candle-light.\n"
                + "I love thee freely, as men strive for Right;\n"
                + "I love thee purely, as they turn from Praise.\n"
                + "I love with a passion put to use\n"
                + "In my old griefs, and with my childhood's faith.\n"
                + "I love thee with a love I seemed to lose\n"
                + "With my lost saints,--I love thee with the breath,\n"
                + "Smiles, tears, of all my life!--and, if God choose,\n"
                + "I shall but love thee better after death.";

        Iterator<String> itr;
        Scanner scanner = new Scanner(new StringReader(poem));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(5));
        assertThat(itr.next(), equalTo("How"));
        assertThat(itr.next(), equalTo("do"));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("thee?"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(4));
        assertThat(itr.next(), equalTo("by"));
        assertThat(itr.next(), equalTo("Elizabeth"));
        assertThat(itr.next(), equalTo("Barrett"));
        assertThat(itr.next(), equalTo("Browning"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(10));
        assertThat(itr.next(), equalTo("How"));
        assertThat(itr.next(), equalTo("do"));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("thee?"));
        assertThat(itr.next(), equalTo("Let"));
        assertThat(itr.next(), equalTo("me"));
        assertThat(itr.next(), equalTo("count"));
        assertThat(itr.next(), equalTo("the"));
        assertThat(itr.next(), equalTo("ways."));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(10));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("thee"));
        assertThat(itr.next(), equalTo("to"));
        assertThat(itr.next(), equalTo("the"));
        assertThat(itr.next(), equalTo("depth"));
        assertThat(itr.next(), equalTo("and"));
        assertThat(itr.next(), equalTo("breadth"));
        assertThat(itr.next(), equalTo("and"));
        assertThat(itr.next(), equalTo("height"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(9));
        assertThat(itr.next(), equalTo("My"));
        assertThat(itr.next(), equalTo("soul"));
        assertThat(itr.next(), equalTo("can"));
        assertThat(itr.next(), equalTo("reach,"));
        assertThat(itr.next(), equalTo("when"));
        assertThat(itr.next(), equalTo("feeling"));
        assertThat(itr.next(), equalTo("out"));
        assertThat(itr.next(), equalTo("of"));
        assertThat(itr.next(), equalTo("sight"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(8));
        assertThat(itr.next(), equalTo("For"));
        assertThat(itr.next(), equalTo("the"));
        assertThat(itr.next(), equalTo("ends"));
        assertThat(itr.next(), equalTo("of"));
        assertThat(itr.next(), equalTo("Being"));
        assertThat(itr.next(), equalTo("and"));
        assertThat(itr.next(), equalTo("ideal"));
        assertThat(itr.next(), equalTo("Grace."));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(9));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("thee"));
        assertThat(itr.next(), equalTo("to"));
        assertThat(itr.next(), equalTo("the"));
        assertThat(itr.next(), equalTo("level"));
        assertThat(itr.next(), equalTo("of"));
        assertThat(itr.next(), equalTo("every"));
        assertThat(itr.next(), equalTo("day's"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(7));
        assertThat(itr.next(), equalTo("Most"));
        assertThat(itr.next(), equalTo("quiet"));
        assertThat(itr.next(), equalTo("need,"));
        assertThat(itr.next(), equalTo("by"));
        assertThat(itr.next(), equalTo("sun"));
        assertThat(itr.next(), equalTo("and"));
        assertThat(itr.next(), equalTo("candle-light."));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(9));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("thee"));
        assertThat(itr.next(), equalTo("freely,"));
        assertThat(itr.next(), equalTo("as"));
        assertThat(itr.next(), equalTo("men"));
        assertThat(itr.next(), equalTo("strive"));
        assertThat(itr.next(), equalTo("for"));
        assertThat(itr.next(), equalTo("Right;"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(9));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("thee"));
        assertThat(itr.next(), equalTo("purely,"));
        assertThat(itr.next(), equalTo("as"));
        assertThat(itr.next(), equalTo("they"));
        assertThat(itr.next(), equalTo("turn"));
        assertThat(itr.next(), equalTo("from"));
        assertThat(itr.next(), equalTo("Praise."));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(8));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("with"));
        assertThat(itr.next(), equalTo("a"));
        assertThat(itr.next(), equalTo("passion"));
        assertThat(itr.next(), equalTo("put"));
        assertThat(itr.next(), equalTo("to"));
        assertThat(itr.next(), equalTo("use"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(9));
        assertThat(itr.next(), equalTo("In"));
        assertThat(itr.next(), equalTo("my"));
        assertThat(itr.next(), equalTo("old"));
        assertThat(itr.next(), equalTo("griefs,"));
        assertThat(itr.next(), equalTo("and"));
        assertThat(itr.next(), equalTo("with"));
        assertThat(itr.next(), equalTo("my"));
        assertThat(itr.next(), equalTo("childhood's"));
        assertThat(itr.next(), equalTo("faith."));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(10));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("thee"));
        assertThat(itr.next(), equalTo("with"));
        assertThat(itr.next(), equalTo("a"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("seemed"));
        assertThat(itr.next(), equalTo("to"));
        assertThat(itr.next(), equalTo("lose"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(11));
        assertThat(itr.next(), equalTo("With"));
        assertThat(itr.next(), equalTo("my"));
        assertThat(itr.next(), equalTo("lost"));
        assertThat(itr.next(), equalTo("saints,"));
        assertThat(itr.next(), equalTo("--"));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("thee"));
        assertThat(itr.next(), equalTo("with"));
        assertThat(itr.next(), equalTo("the"));
        assertThat(itr.next(), equalTo("breath,"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(11));
        assertThat(itr.next(), equalTo("Smiles,"));
        assertThat(itr.next(), equalTo("tears,"));
        assertThat(itr.next(), equalTo("of"));
        assertThat(itr.next(), equalTo("all"));
        assertThat(itr.next(), equalTo("my"));
        assertThat(itr.next(), equalTo("life!"));
        assertThat(itr.next(), equalTo("--"));
        assertThat(itr.next(), equalTo("and,"));
        assertThat(itr.next(), equalTo("if"));
        assertThat(itr.next(), equalTo("God"));
        assertThat(itr.next(), equalTo("choose,"));

        Tokenizer.tokenize(data, scanner.nextLine());
        itr = data.iterator();
        assertThat(data.size(), equalTo(8));
        assertThat(itr.next(), equalTo("I"));
        assertThat(itr.next(), equalTo("shall"));
        assertThat(itr.next(), equalTo("but"));
        assertThat(itr.next(), equalTo("love"));
        assertThat(itr.next(), equalTo("thee"));
        assertThat(itr.next(), equalTo("better"));
        assertThat(itr.next(), equalTo("after"));
        assertThat(itr.next(), equalTo("death."));
 
        scanner.close();
    }

}
