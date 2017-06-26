package org.alicebot.ab;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by chenlei2 on 2017/6/8 0008.
 */
public class ChatRobot {
    public static String sample_file = "sample.random.txt";
    private static ChatRobot instance;

    private Chat chatSession;

    public static ChatRobot getInstance() {
        if (instance == null) {
            instance = new ChatRobot();
        }
        return instance;
    }

    private ChatRobot() {
        MagicStrings.setRootPath();

        AIMLProcessor.extension = new PCAIMLProcessorExtension();
        String botName = "alice2";
        MagicBooleans.jp_tokenize = false;
        MagicBooleans.trace_mode = true;
        String action = "chat";
        System.out.println(MagicStrings.program_name_version);
     /*   for (String s : args) {
            //System.out.println(s);
            String[] splitArg = s.split("=");
            if (splitArg.length >= 2) {
                String option = splitArg[0];
                String value = splitArg[1];
                //if (MagicBooleans.trace_mode) System.out.println(option+"='"+value+"'");
                if (option.equals("bot")) botName = value;
                if (option.equals("action")) action = value;
                if (option.equals("trace")) {
                    if (value.equals("true")) MagicBooleans.trace_mode = true;
                    else MagicBooleans.trace_mode = false;
                }
                if (option.equals("morph")) {
                    if (value.equals("true")) MagicBooleans.jp_tokenize = true;
                    else {
                        MagicBooleans.jp_tokenize = false;
                    }
                }
            }
        }*/
        if (MagicBooleans.trace_mode) System.out.println("Working Directory = " + MagicStrings.root_path);
        Graphmaster.enableShortCuts = true;
        //Timer timer = new Timer();
        Bot bot = new Bot(botName, MagicStrings.root_path, action); //
        //EnglishNumberToWords.makeSetMap(bot);
        //getGloss(bot, "c:/org.alicebot.ab/data/wn30-lfs/wne-2006-12-06.xml");
        if (MagicBooleans.make_verbs_sets_maps) Verbs.makeVerbSetsMaps(bot);
        //bot.preProcessor.normalizeFile("c:/org.alicebot.ab/data/log2.txt", "c:/org.alicebot.ab/data/log2normal.txt");
        //System.exit(0);
        if (bot.brain.getCategories().size() < MagicNumbers.brain_print_size) bot.brain.printgraph();
        if (MagicBooleans.trace_mode) System.out.println("Action = '" + action + "'");
        if (action.equals("chat") || action.equals("chat-app")) {
            boolean doWrites = !action.equals("chat-app");
            chatSession = new Chat(bot, doWrites);
            bot.brain.nodeStats();
        }
        //else if (action.equals("test")) testSuite(bot, MagicStrings.root_path+"/data/find.txt");
        else if (action.equals("org/alicebot/ab")) TestAB.testAB(bot, TestAB.sample_file);
        else if (action.equals("aiml2csv") || action.equals("csv2aiml")) convert(bot, action);
        else if (action.equals("abwq")) {
            AB ab = new AB(bot, TestAB.sample_file);
            ab.abwq();
        } else if (action.equals("test")) {
            TestAB.runTests(bot, MagicBooleans.trace_mode);
        } else if (action.equals("shadow")) {
            MagicBooleans.trace_mode = false;
            bot.shadowChecker();
        } else if (action.equals("iqtest")) {
            ChatTest ct = new ChatTest(bot);
            try {
                ct.testMultisentenceRespond();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else System.out.println("Unrecognized action " + action);
    }

    public void convert(Bot bot, String action) {
        if (action.equals("aiml2csv")) bot.writeAIMLIFFiles();
        else if (action.equals("csv2aiml")) bot.writeAIMLFiles();
    }

    public static void sraixCache(String filename, Chat chatSession) {
        int limit = 650000;
        MagicBooleans.cache_sraix = true;
        try {
            FileInputStream fstream = new FileInputStream(filename);
            // Get the object
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            //Read File Line By Line
            int count = 0;
            while ((strLine = br.readLine()) != null && count++ < limit) {
                System.out.println("Human: " + strLine);

                String response = chatSession.multisentenceRespond(strLine);
                System.out.println("Robot: " + response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testAB(Bot bot, String sampleFile) {
        MagicBooleans.trace_mode = true;
        AB ab = new AB(bot, sampleFile);
        ab.ab();
        System.out.println("Begin Pattern Suggestor Terminal Interaction");
        ab.terminalInteraction();
    }

    public String chat(String textLine) {
  //      textLine = IOUtils.readInputTextLine("Human");
        if (textLine == null || textLine.length() < 1) textLine = MagicStrings.null_input;
        String request = textLine;
        if (MagicBooleans.trace_mode)
            System.out.println("STATE=" + request + ":THAT=" + chatSession.thatHistory.get(0).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
        String response = chatSession.multisentenceRespond(request);
        while (response.contains("&lt;")) response = response.replace("&lt;", "<");
        while (response.contains("&gt;")) response = response.replace("&gt;", ">");
        return response;
       // IOUtils.writeOutputTextLine("Robot", response);
        //System.out.println("Learn graph:");
        //bot.learnGraph.printgraph();

    }

}
