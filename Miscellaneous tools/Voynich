package com.cesar.voynich;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class VoynichLoader 
{
	public Voynich load(File file) throws IOException
	{
		long inicial = System.currentTimeMillis();
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "";
		String[] words;
		Voynich voynich = new Voynich(); 
		while((line = reader.readLine()) != null)
		{
			if(line.length() < 5)
				continue;
			
			
			String code1 = " ";
			String code2 = " ";
			
			if(line.indexOf("r") != -1)
				code1 = line.substring(0, line.indexOf("r"));
			else
				code1 = "       ";
			if(line.indexOf("v") != -1)
				code2 = line.substring(0, line.indexOf("v"));
			else
				code2 = "       ";
			
			String code = code1.length() > code2.length() ? code2 : code1;
			String pageValue = "";
			for(char l : code.toCharArray())
			{
				try
				{
					Integer v = Integer.valueOf(String.valueOf(l));
					pageValue += v;
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			words = line.replaceAll("!", "")
					.replaceAll("\\<.*?\\>", "")
					.replaceAll(" ", "")
					.replaceAll(",", "")
					.replaceAll("\\*", "")
					.trim()
					.split("\\.");
			List<String> list = convert(words);
			voynich.words.addAll(list);
			
			voynich.pages.put(Integer.valueOf(pageValue), voynich.words.size());
		}
		
		return voynich;
	}

	private List<String> convert(String[] words) {
		List<String> list = new ArrayList<String>(words.length);
		
		for(int i=0; i < words.length; i++)
		{
			if(words[i].equals(""))
				continue;
			list.add(words[i]);
		}
		return list;
	}
}
