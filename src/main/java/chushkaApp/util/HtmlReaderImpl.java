package chushkaApp.util;

import java.io.*;

public class HtmlReaderImpl implements HtmlReader {
    private static final String DEFAULT_VIEW_PATH =
            "C:\\Users\\Stefan\\IdeaProjects\\JavaEE Web\\ChushkaApp\\src\\main\\resources\\views\\%s.html";

    @Override
    public String readHtmlFile(String htmlFileName) throws IOException {
        htmlFileName = String.format(DEFAULT_VIEW_PATH, htmlFileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(htmlFileName))));

        StringBuilder htmlContent = new StringBuilder();

        String fileLine;

        while ((fileLine = reader.readLine()) != null) {
            htmlContent.append(fileLine).append(System.lineSeparator());
        }

        return htmlContent.toString();
    }
}
