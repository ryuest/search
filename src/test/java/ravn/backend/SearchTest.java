package ravn.backend; /**
 * Created by jboiko on 18/01/2018.
 */

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

import com.google.common.io.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;

import static ravn.backend.Search.createSearchResult;
import static ravn.backend.Search.getNamedStringList;


class SearchTest {

    ImmutableList<String> immutable;
    String[] args;

    @BeforeEach
    public void begin() throws IOException {

     File file = new File("src//test//resources//exercise_data.csv");

     immutable = Files.asCharSource(file, Charsets.UTF_8)
                .readLines();

        args = new String[] {"een","een"};
    }


    @Test
    public void testHeight() {
        ListMultimap<String, String> result = createSearchResult(getNamedStringList(immutable), args);
        Assertions.assertEquals("1005eb104f45e86e047221ffd54f93e90051bc46", result.asMap().keySet().toArray()[0]);
        Assertions.assertEquals("[Book of Mormon musical storms West End, Book of Mormon musical storms West End]",result.asMap().values().toArray()[0].toString() );
    }
}
