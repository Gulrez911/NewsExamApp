package com.ctet.controllers;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctet.data.Question;
import com.ctet.services.QuestionService;

@Controller
public class CtetController {

	@Autowired
	QuestionService questionService;

	@RequestMapping(value = "/ctetContent", method = RequestMethod.GET)
	public @ResponseBody String ctetContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String loc = "D:\\GulDoc\\QuestionList\\CDP";
		String loc = "D:\\GulDoc\\QuestionList\\FileSever";
//		String loc ="/opt/gul/FileSever";
		File dir = new File(loc);
		List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File file : files) {
			List<Question> qs = extractQuestions(file);
			for (Question q : qs) {
				q.setup();
				questionService.createQuestion(q);
			}

		}
		System.out.println("Process Complete");
		FileUtils.write(new File("ctetContent.out"), "********* file processing complete", true);
		return "OK";
	}

	public List<Question> extractQuestions(File file) throws Exception {
		List<Question> questions = new ArrayList<Question>();
		System.out.println("?????????????????????????????  " + file);
		LinkedList<String> answers = (LinkedList<String>) extractAnswers(file);
		String ss = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		String lines[] = ss.split("\\r?\\n");
		String q1= lines[0] ;
		String q2= lines[1] ;
		int c0unt2 = 1;
		for (int i = 1; i <= answers.size(); i++) {
			Question question = new Question();
			String q = ss.substring(ss.indexOf(i + "."), ss.indexOf(("(a)"))).trim().replace("\n", "").replace("\r", " ");
			String s1 = i + ".";
			int sepPos1 = q.indexOf(s1);
			q = q.substring(sepPos1 + s1.length()).trim();
			String a1 = ss.substring(ss.indexOf("(a)"), ss.indexOf(("(b)"))).trim().replace("\n", "").replace("\r", " ");
			String s2 =  "(a)";
			int sepPos2 = a1.indexOf(s2);
			a1 = a1.substring(sepPos2 + s2.length()).trim();
			String b1 = ss.substring(ss.indexOf("(b)"), ss.indexOf(("(c)"))).trim().replace("\n", "").replace("\r", " ");
			String s3 =  "(b)";
			int sepPos3 = b1.indexOf(s3);
			b1 = b1.substring(sepPos3 + s3.length()).trim();
			String c1 = ss.substring(ss.indexOf("(c)"), ss.indexOf("(d)")).trim().replace("\n", "").replace("\r", " ");
			String s4 =  "(c)";
			int sepPos4 = c1.indexOf(s4);
			c1 = c1.substring(sepPos4 + s4.length()).trim();
			String d1 = "";
			if (i == answers.size()) {
				d1 = ss.substring(ss.indexOf("(d)"), ss.indexOf("KEY")).trim().replace("\n", "").replace("\r", " ");
			} else {
				d1 = ss.substring(ss.indexOf("(d)"), ss.indexOf(i + 1 + ".")).trim().replace("\n", "").replace("\r", " ");
			}
			String s5 =  "(d)";
			int sepPos5 = d1.indexOf(s5);
			d1 = d1.substring(sepPos5 + s5.length()).trim();
			String right = getRightAnswer(i, answers);
			System.out.println("////////     " + right);
//		String  temp = ss.substring(   ss.indexOf(i+3+")"), ss.indexOf(i+1+")")         );
			System.out.println("  ...  " + q);
			System.out.println("  ...  " + a1);
			System.out.println("  ...  " + b1);
			System.out.println("  ...  " + c1);
			System.out.println("  ...  " + d1 + "\n");

			question.setQuestionText(q);
			question.setChoice1(a1);
			question.setChoice2(b1);
			question.setChoice3(c1);
			question.setChoice4(d1);
//			question.setQualifier1("CTET");
//			question.setQualifier2("CDP");
			question.setQualifier1(q1);
			question.setQualifier2(q2);
			question.setRightChoices(right);
			question.setCompanyId("e-assess");
			question.setCompanyName("e-assess");
			c0unt2++;
			if (i == 84) {
				System.out.println("..................................");
			}
			if (c0unt2 <= answers.size()) {
				ss = ss.substring(ss.indexOf(c0unt2 + "."), ss.length()).trim();
			}
			questions.add(question);
			System.out.println("?????????????????????????????  " + file);
		}
		System.out.println("?????????????????????????????  " + file);
		return questions;
	}

	public static List<String> extractAnswers(File file) throws Exception {
		LinkedList<String> answers = new LinkedList<>();
		String ss = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		String result = ss.substring(ss.indexOf("KEY") + 5, ss.length()).replaceAll("\\s", "");
		String pattern = "\\d{1,3}{1,}([a-d])";
//		String pattern = "\\d{1,2}([\\.])[ ]{1,}[(]([a-d])[)]";
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(result);
		int count = 0;
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			answers.add(result.substring(start, end));
			count++;
			// System.out.println("found: " + count +"- "+text.substring(start, end));

		}
		System.out.println("         " + count);
		return answers;
	}

	private String getRightAnswer(int quesNo, List<String> answers) throws Exception {
		String temp2 = "";
		for (int i = 0; i < answers.size(); i++) {

			String temp = answers.get(i);
			temp = temp.substring(temp.length() - 1);
			if (quesNo == i + 1) {

				if (temp.equals("a")) {
					temp2 = "Choice 1";
				} else if (temp.equals("b")) {
					temp2 = "Choice 2";
				} else if (temp.equals("c")) {
					temp2 = "Choice 3";
				} else if (temp.equals("d")) {
					temp2 = "Choice 4";
				} else {
//					throw new AssessmentGenericException("Problem - no right answer");
				}
			}

		}
//		System.out.println("....                     "+temp2);
		return temp2;

	}
}