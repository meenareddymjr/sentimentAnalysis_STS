package com.sentimentanalysis.process;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class SentenceTokenClass {
	
public static String sentenceDetect(String tokenText) throws InvalidFormatException, IOException {
    	
        tokenText=tokenText.replaceAll("\\p{Punct}", "");
        
        // refer to model file "en-token.bin" 
        InputStream tokenIs = new FileInputStream("lib\\en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(tokenIs);
        
        InputStream inputStream = new 
                FileInputStream("lib\\en-pos-maxent.bin"); 
        POSModel posModel = new POSModel(inputStream);
 
        TokenizerME tokenizer = new TokenizerME(tokenModel);
        
        POSTaggerME tagger = new POSTaggerME(posModel);
        
        String tokens[] = tokenizer.tokenize(tokenText);
        
        String[] tags = tagger.tag(tokens);
        
        List<String> list = new ArrayList<String>(Arrays.asList(tags));
        List<String> listToken = new ArrayList<String>(Arrays.asList(tokens));
        for(int i=0; i<list.size();i++) {
        	if(list.get(i).equals("DT")) {
        		list.remove(i);
        		listToken.remove(i);
        	}
        }
        tags = list.toArray(new String[0]);
        tokens = listToken.toArray(new String[0]);
        
        int positive=0,negative=0,neutral=0;
        	for(int i=0;i<tags.length;i++) {
        		if(!(tags[i].contains("NN") || tags[i].contains("PRP") || tags[i].contains("TO") || tags[i].contains("DT"))) {
        			if(tokens[i].equalsIgnoreCase("very")) {
        				positive=positive+1;
        			}
        			if(tokens[i].equalsIgnoreCase("not")) {
        				negative = negative-1;
        			}
        		if(FileReaderClass.readDocxFile("KeywordsFiles\\Positive Words.docx",tokens[i])) {
        			if(i>0 && tokens[i-1].equalsIgnoreCase("very")) {
        				positive=positive+3;
        			}
        			else if(i>0 && tokens[i-1].equalsIgnoreCase("not")) {
        				negative = negative-2;
        			}
        			else {
        				positive = positive+1;
        			}
        		}
        		else if(FileReaderClass.readDocxFile("KeywordsFiles\\Negative Words.docx",tokens[i])) {
        			if(i>0 && tokens[i-1].equalsIgnoreCase("very")) {
        				negative = negative-3;
        			}
        			else if(i>0 && tokens[i-1].equalsIgnoreCase("not")) {
        				positive = positive+2;
        			}
        			else {
        				negative = negative-1;
        			}
        		}
        		else {
        			neutral=0;
        		}
        	}
        }
        String reaction = positive+negative+neutral>=1?"Positive":(positive+negative+neutral<0?"Negative":"Neutral");
        tokenIs.close();
        return reaction;
    }
}

