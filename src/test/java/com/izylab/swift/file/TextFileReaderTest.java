package com.izylab.swift.file;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/** Test file reader. */
public class TextFileReaderTest {

    @Test
    public void loadFileTest() {
        FileReader fr = new TextFileReader("/Through the Looking-Glass - Carroll Lewis.txt");
        assertThat(fr.hasNext(), equalTo(true));
    }
    
    @Test
    public void getTokenTest() {
        FileReader fr = new TextFileReader("/Through the Looking-Glass - Carroll Lewis.txt");
        assertThat(fr.hasNext(), equalTo(true));
        String str = fr.getNext();
        assertThat(str, equalTo("The"));
        assertThat(fr.getNext(), equalTo("Project"));
        assertThat(fr.getNext(), equalTo("Gutenberg"));
        assertThat(fr.getNext(), equalTo("EBook"));
        assertThat(fr.getNext(), equalTo("of"));
        assertThat(fr.getNext(), equalTo("Through"));
        assertThat(fr.getNext(), equalTo("the"));
        assertThat(fr.getNext(), equalTo("Looking-Glass,"));
        assertThat(fr.getNext(), equalTo("by"));
        assertThat(fr.getNext(), equalTo("Charles"));
        assertThat(fr.getNext(), equalTo("Dodgson"));
        assertThat(fr.getNext(), equalTo("AKA"));
        assertThat(fr.getNext(), equalTo("Lewis"));
        assertThat(fr.getNext(), equalTo("Carroll"));
        assertThat(fr.getNext(), equalTo("This"));
        assertThat(fr.getNext(), equalTo("eBook"));
        assertThat(fr.getNext(), equalTo("is"));
        assertThat(fr.getNext(), equalTo("for"));
        assertThat(fr.getNext(), equalTo("the"));
        assertThat(fr.getNext(), equalTo("use"));
        assertThat(fr.getNext(), equalTo("of"));
        assertThat(fr.getNext(), equalTo("anyone"));
        assertThat(fr.getNext(), equalTo("anywhere"));
        assertThat(fr.getNext(), equalTo("at"));
        assertThat(fr.getNext(), equalTo("no"));
        assertThat(fr.getNext(), equalTo("cost"));
        assertThat(fr.getNext(), equalTo("and"));
        assertThat(fr.getNext(), equalTo("with"));
        assertThat(fr.getNext(), equalTo("almost"));
    }

}
