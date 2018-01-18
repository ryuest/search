package ravn.backend; /**
 * Created by jboiko on 16/01/2018.
 */
import com.google.common.base.Charsets;
import com.google.common.collect.*;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import java.util.Collection;


public class Search {

    public static void main(String[] args) throws IOException {

        try {
            String[] searchTerms = new String[2];
            searchTerms[0] = args[1];
            if (2 >= args.length) {
                searchTerms[1] = args[1];
            } else {
                searchTerms[1] = args[2];
            }

            File file = new File(args[0]);

            ImmutableList<String> lines = Files.asCharSource(file, Charsets.UTF_8)
                    .readLines();

            ListMultimap<String, String> result = createSearchResult(getNamedStringList(lines), searchTerms);

            for (int x = 0; x < result.asMap().size(); x++) {
                System.out.println("<" + result.asMap().keySet().toArray()[x] + ">,"
                        + "<" + result.asMap().values().toArray()[x].toString() + ">");
                System.out.println("");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing program arguments. Example: \"src//main//resources//exercise_data.csv\" \"term1\" \"term2\"");
        }
    }

    public static ImmutableMultimap.Builder<String, String> getNamedStringList(ImmutableList<String> lines) {
        ImmutableMultimap.Builder<String, String> builder = ImmutableMultimap.builder();
        for (String s : lines) {
            String[] line = s.trim().split("\",\"");
            builder.put("id", line[0].replaceAll("\"", ""));
            builder.put("title", line[1].replaceAll("\"", ""));
            builder.put("text", line[2].replaceAll("\"", ""));
        }
        return builder;
    }

    public static ListMultimap<String, String> createSearchResult(ImmutableMultimap.Builder<String, String> builder, String[] searchTerms) {

        ListMultimap<String, String> result =
                MultimapBuilder.treeKeys().arrayListValues().build();
        ImmutableMap<String, Collection<String>> documentsMap = builder.build().asMap();
        for (int x = 0; x < documentsMap.size(); x++) {
            if (findDocument(documentsMap, x, "text").toLowerCase().contains(searchTerms[0].toLowerCase()) && findDocument(documentsMap, x, "text").toLowerCase().contains(searchTerms[1].toLowerCase())) {
                result.put(findDocument(documentsMap, x, "id"), findDocument(documentsMap, x, "title"));
            }
        }
        return result;
    }

    public static String findDocument(ImmutableMap<String, Collection<String>> documentsMap, int number, String type) {
        return documentsMap.get(type).toArray()[number].toString();
    }
}
