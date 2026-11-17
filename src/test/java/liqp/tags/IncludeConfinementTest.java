package liqp.tags;

import liqp.RenderSettings;
import liqp.Template;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IncludeConfinementTest {

    // GAP-PIPE-001: "sub/../../secret.txt" walked out of the includes directory and rendered any local file.
    @Test
    public void includeCannotLeaveTheIncludesDirectory() throws IOException {
        File root = Files.createTempDirectory("liqp-include").toFile();
        File includes = new File(root, "includes");
        assertTrue(new File(includes, "sub").mkdirs());
        Files.write(new File(includes, "inside.liquid").toPath(), "INSIDE".getBytes(StandardCharsets.UTF_8));
        Files.write(new File(root, "secret.txt").toPath(), "LOCAL-FILE-CANARY".getBytes(StandardCharsets.UTF_8));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Include.INCLUDES_DIRECTORY_KEY, includes.getAbsolutePath());
        RenderSettings quiet = new RenderSettings.Builder().withShowExceptionsFromInclude(false).build();

        String escaped = Template.parse("[{% include 'sub/../../secret.txt' %}]").withRenderSettings(quiet).render(data);
        String inside = Template.parse("[{% include 'inside' %}]").withRenderSettings(quiet).render(data);

        assertThat(escaped, is("[]"));
        assertThat(inside, is("[INSIDE]"));
    }
}
