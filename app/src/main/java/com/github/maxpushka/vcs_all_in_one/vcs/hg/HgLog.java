package com.github.maxpushka.vcs_all_in_one.vcs.hg;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSLogParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.hg.Hg.hgBuilder;

class HgLog implements Callable<ArrayList<VCSCommit>> {
    private static final VCSLogParser logParser = rawLog -> {
        // convert raw log to input stream
        InputStream is = new ByteArrayInputStream(String.join("", rawLog).getBytes());

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // optional, but recommended
        // process XML securely, avoid attacks like XML External Entities (XXE)
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        // parse XML file
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(is);

        NodeList xmlLog = doc.getElementsByTagName("logentry");

        ArrayList<VCSCommit> commits = new ArrayList<>();
        for (var i = 0; i < xmlLog.getLength(); i++) {
            var xmlEntry = xmlLog.item(i);

            var revision = ((Element) xmlEntry).getAttribute("node");
            var commitMsg = xmlEntry.getChildNodes().item(2).getTextContent();

            commits.add(new VCSCommit(revision, commitMsg));
        }

        return commits;
    };

    static ArrayList<VCSCommit> customLog(CommandArg... customOpts) throws Exception {
        CommandBuilder builder = hgBuilder()
                .addArguments(
                        new CommandArg("log"),
                        new CommandArg("-Txml"))
                .addArguments(customOpts);
        var rawLog = new CommandLine(builder).call();
        return logParser.parse(rawLog);
    }

    @Override
    public ArrayList<VCSCommit> call() throws Exception {
        return customLog();
    }
}
