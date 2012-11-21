package isthere.local.test;

import isthere.StringIndexCreatorThread;
import org.apache.commons.cli.*;

/**
 * Serverside index creator.
 * User: Hyunje
 * Date: 12. 11. 15
 * Time: 오전 2:25
 */
public class ServersideIndexCreator {

    public static String input = "";
    public static String output = "";
    public static String mD = "::";
    public static String sD = "||";

    private static final String[][] requiredOptions =
            {
                    {"input", "Pleas input path directory has training data"},
                    {"output", "Please input output path and filename for index"},
                    {"maindelim", "Please input main delimiter for index (default is '::')"},
                    {"semidelim", "Please input semi delimiter for index (default is '||'"}
            };

    /**
     * Set options.
     *
     * @return Options
     */
    private static Options getOptions() {
        Options options = new Options();
        options.addOption("i", "input", true, "input path for a directory (required)");
        options.addOption("o", "output", true, "output path include filename (required)");
        options.addOption("md", "maindelim", true, "main delimiter (required)");
        options.addOption("sd", "semidelim", true, "semi delimiter (required)");
        return options;
    }

    public static void main(String[] args) throws Exception {
        Options options = getOptions();

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);
        HelpFormatter formatter = new HelpFormatter();

        for (String[] requiredOption : requiredOptions) {
            if (!cmd.hasOption(requiredOption[0])) {
                System.out.println("in if");
                formatter.printHelp("java jar <JAR> ", options, true);
                return;
            }
        }

        input = cmd.getOptionValue("i");
        output = cmd.getOptionValue("o");
        mD = cmd.getOptionValue("md");
        sD = cmd.getOptionValue("sd");

        StringIndexCreatorThread indexingThread = new StringIndexCreatorThread(input, output, mD,sD);
        indexingThread.start();
    }
}
