package CodeRatio.BoldTags.Calculation.CalculateHTMLTags;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CalculateHTMLTags {

	static final String MacOSXPathToReadFile = "/Users/manikhossain/EclipseCreatedSOJavaFile/CodeRatio/QuestionsAllClonedData.csv";
	static String postIDAsFileName = "";
	static String bodyOfCode = "";
	static String CombineAllCode = "";
	static int countTotal = 1;
	static String CSVbuilder = null;

	public static void main(String[] args) throws IOException {
		String filename = MacOSXPathToReadFile;
		FileWriter writer = new FileWriter("/Users/manikhossain/QuestionsCodeRatioFromTags1.csv");
		FileWriter writer2 = new FileWriter("/Users/manikhossain/QuestionsCodeRatioFromTags2.csv");

		Scanner sc = new Scanner(new File(filename));
		while (sc.hasNext()) {
			String data = sc.nextLine();

			if (!data.toLowerCase().contentEquals("body")) {
				if (data.startsWith("\"")) { // indicate starting of each row value
					data = data.substring(1); // remove first char because at the beginning " this char comes
					if (data.contains("<postid>") && data.split("<postid>").length > 1) {
						postIDAsFileName = data.split("<postid>")[0];
						bodyOfCode = data.split("<postid>")[1];
					} else {
						bodyOfCode = data;
					}
					countTotal += 1;
					// System.out.println("codeRatio: " + data);
					extractHtmlCodeTags(postIDAsFileName, bodyOfCode, writer, writer2);
				}
			}

		}
		sc.close();
		writer.close();
		writer2.close();
		System.out.println("countTotal: " + countTotal);

	}

	private static void extractHtmlCodeTags(String postId, String combineAllCode, FileWriter writer, FileWriter writer2)
			throws IOException {
		double totalCodeLength = 0;
		int NoOfcodeSnippet = 0;
		int strongTags = 0;
		int hrefTags = 0;
		int emfTags = 0;
		double totalBodylength = combineAllCode.length();
		String codeRatio = "";
		String codeRatioPercentage = "";
		String[] splitCOde = combineAllCode.split("<code>");
		for (String partByPartCode : splitCOde) {
			String[] departedCode = partByPartCode.split("</code>");
			if (departedCode.length > 1) {
				totalCodeLength = totalCodeLength + departedCode[0].length();
			}
		}
		codeRatio = String.format("%.4f", (totalCodeLength / totalBodylength));
		codeRatioPercentage = String.format("%.4f", (totalCodeLength / totalBodylength)*100);
		NoOfcodeSnippet = splitCOde.length;

		if (combineAllCode.contains("strong")) {
			String[] strongTag = combineAllCode.split("<strong>");
			strongTags = strongTag.length - 1;
		}
		if (combineAllCode.contains("href")) {
			String[] hrefTag = combineAllCode.split("href");
			hrefTags = hrefTag.length - 1;
		}
		if (combineAllCode.contains("<em>")) {
			String[] emfTag = combineAllCode.split("<em>");
			emfTags = emfTag.length - 1;
		}

		CSVbuilder = postId + "," + NoOfcodeSnippet + "," + strongTags + "," + hrefTags + "," + emfTags + ","
				+ totalBodylength + "," + Double.valueOf(codeRatio) + "," + Double.valueOf(codeRatioPercentage) + ","
				+ totalCodeLength;
		
		if (countTotal <= 65534) {
			writer.write("\n");
			writer.write(CSVbuilder);

		} else {
			writer2.write("\n");
			writer2.write(CSVbuilder);
		}
		
		

		//System.out.println("CSVbuilder: " + CSVbuilder);
	}
//strong, em, href, 

}
